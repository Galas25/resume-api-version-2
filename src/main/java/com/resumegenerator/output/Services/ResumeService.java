package com.resumegenerator.output.Services;

import com.resumegenerator.output.DTOs.ResumePdfDto;
import com.resumegenerator.output.Models.*;
import com.resumegenerator.output.Repositories.ResumeRepository;
import com.resumegenerator.output.Requests.CreateResumeRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.lowagie.text.pdf.draw.LineSeparator;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;

    // PDF Fonts
    private static final Font TITLE_FONT = new Font(Font.HELVETICA, 24, Font.BOLD, new Color(44, 62, 80));
    private static final Font SUBTITLE_FONT = new Font(Font.HELVETICA, 12, Font.NORMAL, Color.GRAY);
    private static final Font SECTION_HEADER_FONT = new Font(Font.HELVETICA, 14, Font.BOLD, Color.WHITE);
    private static final Font BODY_FONT = new Font(Font.HELVETICA, 11, Font.NORMAL, Color.BLACK);
    private static final Font BOLD_BODY_FONT = new Font(Font.HELVETICA, 11, Font.BOLD, Color.BLACK);

    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    // ---------------- CRUD ----------------

    @Transactional
    public Resume createResume(CreateResumeRequest request) {
        Resume resume = new Resume();

        // Professional Summary
        ProfessionalSummary ps = new ProfessionalSummary();
        ps.setSummary(request.getProfessionalSummary());
        ps.setResume(resume);
        resume.setProfessionalSummary(ps);

        // Experience
        Experience exp = new Experience();
        exp.setExperience(request.getExperience());
        exp.setResume(resume);
        resume.setExperience(exp);

        // Education
        Education edu = new Education();
        edu.setInstitution(request.getInstitution());
        edu.setCompletionDate(request.getCompletionDate());
        edu.setResume(resume);
        resume.setEducation(edu);

        // Skills
        Skills skills = new Skills();
        skills.setSkills(request.getSkills());
        skills.setResume(resume);
        resume.setSkills(skills);

        return resumeRepository.save(resume);
    }

    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }

    @Transactional
    public Resume updateResumebyID(Long resumeId, CreateResumeRequest request) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new RuntimeException("Resume not found: " + resumeId));

        PersonalInformation pi = resume.getPersonalInformation();
        Skills skills = resume.getSkills();
        Education education = resume.getEducation();
        ProfessionalSummary ps = resume.getProfessionalSummary();
        Experience exp = resume.getExperience();

        if (pi == null) {
            pi = new PersonalInformation();
            pi.setResume(resume);
            resume.setPersonalInformation(pi);
        }
        pi.setFirstName(request.getFirstName());
        pi.setMiddleName(request.getMiddleName());
        pi.setLastName(request.getLastName());
        pi.setSuffix(request.getSuffix());
        pi.setEmail(request.getEmail());
        pi.setPhone(request.getPhone());
        pi.setAddress(request.getAddress());

        if (ps == null) {
            ps = new ProfessionalSummary();
            ps.setResume(resume);
            resume.setProfessionalSummary(ps);
        }
        ps.setSummary(request.getProfessionalSummary());

        if (exp == null) {
            exp = new Experience();
            exp.setResume(resume);
            resume.setExperience(exp);
        }
        exp.setExperience(request.getExperience());

        if (education == null) {
            education = new Education();
            education.setResume(resume);
            resume.setEducation(education);
        }
        education.setInstitution(request.getInstitution());
        education.setCompletionDate(request.getCompletionDate());

        if (skills == null) {
            skills = new Skills();
            skills.setResume(resume);
            resume.setSkills(skills);
        }
        skills.setSkills(request.getSkills());

        return resume;
    }

    @Transactional
    public void deleteResume(Long resumeId) {
        if (!resumeRepository.existsById(resumeId)) {
            throw new RuntimeException("Resume not found: " + resumeId);
        }
        resumeRepository.deleteById(resumeId);
    }

    @Transactional
    public void deleteAllResumes() {
        resumeRepository.deleteAll();
    }

    // ---------------- PDF Generation ----------------

    public byte[] generateResumePdf(ResumePdfDto resume) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4, 40, 40, 40, 40);
            PdfWriter.getInstance(document, out);
            document.open();

            // --- Personal Info ---
            if (resume.getPersonalInformation() != null) {
                PersonalInformation pi = resume.getPersonalInformation();
                document.add(new Paragraph("\n"));
                addHeader(document, pi);
                addEmptyLine(document, 1);
            }


            // --- Professional Summary ---
            if (resume.getProfessionalSummary() != null) {
                addSectionTitle(document, "PROFESSIONAL SUMMARY");
                document.add(new Paragraph(resume.getProfessionalSummary().getSummary(), BODY_FONT));
                addEmptyLine(document, 1);
            }

            // --- Experience ---
            if (resume.getExperience() != null) {
                addSectionTitle(document, "EXPERIENCE");
                document.add(new Paragraph(resume.getExperience().getExperience(), BODY_FONT));
                addEmptyLine(document, 1);
            }

            // --- Education ---
            if (resume.getEducation() != null) {
                addSectionTitle(document, "EDUCATION");
                PdfPTable table = new PdfPTable(2);
                table.setWidthPercentage(100);
                table.setWidths(new float[]{1, 3});
                addEducationRow(table, "Institution", resume.getEducation().getInstitution());
                addEducationRow(table, "Completion Date", resume.getEducation().getCompletionDate());
                document.add(table);
                addEmptyLine(document, 1);
            }

            // --- Skills ---
            if (resume.getSkills() != null) {
                addSectionTitle(document, "SKILLS");
                document.add(new Paragraph(resume.getSkills().getSkills(), BODY_FONT));
                addEmptyLine(document, 1);
            }

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }

    // ---------------- PDF Helpers ----------------

    private void addHeader(Document document, PersonalInformation info) throws DocumentException {
        if (info == null) return;

        String fullName = info.getFirstName() + " " +
                (info.getMiddleName() != null ? info.getMiddleName() + " " : "") +
                info.getLastName() +
                (info.getSuffix() != null ? " " + info.getSuffix() : "");

        Paragraph name = new Paragraph(fullName.toUpperCase(), TITLE_FONT);
        name.setAlignment(Element.ALIGN_CENTER);
        document.add(name);

        String contact = "";
        if (info.getEmail() != null) contact += info.getEmail();
        if (info.getPhone() != null) contact += " | " + info.getPhone();
        if (info.getAddress() != null) contact += " | " + info.getAddress();

        Paragraph contactParagraph = new Paragraph(contact, SUBTITLE_FONT);
        contactParagraph.setAlignment(Element.ALIGN_CENTER);
        contactParagraph.setSpacingAfter(10f);
        document.add(contactParagraph);

        LineSeparator ls = new LineSeparator();
        ls.setLineColor(Color.LIGHT_GRAY);
        document.add(new Chunk(ls));
        addEmptyLine(document, 1);
    }

    private void addSectionTitle(Document document, String title) throws DocumentException {
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);

        PdfPCell cell = new PdfPCell(new Phrase(title, SECTION_HEADER_FONT));
        cell.setBackgroundColor(new Color(44, 62, 80));
        cell.setPadding(5);
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

        document.add(table);
        document.add(new Paragraph(" ", new Font(Font.HELVETICA, 4)));
    }

    private void addEducationRow(PdfPTable table, String label, String value) {
        if (value == null) return;

        PdfPCell labelCell = new PdfPCell(new Phrase(label, BOLD_BODY_FONT));
        labelCell.setBorder(Rectangle.NO_BORDER);
        labelCell.setPaddingBottom(5);

        PdfPCell valueCell = new PdfPCell(new Phrase(value, BODY_FONT));
        valueCell.setBorder(Rectangle.NO_BORDER);
        valueCell.setPaddingBottom(5);

        table.addCell(labelCell);
        table.addCell(valueCell);
    }

    private void addEmptyLine(Document document, int number) throws DocumentException {
        for (int i = 0; i < number; i++) {
            document.add(new Paragraph(" "));
        }
    }
}
