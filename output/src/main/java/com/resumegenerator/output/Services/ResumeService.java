package com.resumegenerator.output.Services;
import com.resumegenerator.output.Models.PersonalInformation;
import com.resumegenerator.output.Models.Resume;
import com.resumegenerator.output.Repositories.ResumeRepository;
import com.resumegenerator.output.Requests.CreateResumeRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
//http//:localhost:8080/api/resume
public class ResumeService {

    private final ResumeRepository resumeRepository;

    public ResumeService(ResumeRepository resumeRepository) {
        this.resumeRepository = resumeRepository;
    }

    @Transactional
    public Resume createResume(CreateResumeRequest request) {
        Resume resume = new Resume();

        PersonalInformation pi = new  PersonalInformation();
        pi.setFirstName(request.getFirstName());
        pi.setMiddleName(request.getMiddleName());
        pi.setLastName(request.getLastName());
        pi.setEmail(request.getEmail());
        pi.setPhone(request.getPhone());
        pi.setAddress(request.getAddress());
        pi.setResume(resume);

        resume.setPersonalInformation(pi);

        return resumeRepository.save(resume);
    }

    public List<Resume> getAllResumes() {
        return resumeRepository.findAll();
    }

    @Transactional
    public Resume updateResumebyID(Long resumeId, CreateResumeRequest request) {
        Resume resume = resumeRepository.findById(resumeId).orElseThrow(() -> new RuntimeException("Resume not found: " + resumeId));

        PersonalInformation pi = resume.getPersonalInformation();

        if (pi == null) {
            pi = new PersonalInformation();
            pi.setResume(resume);
            resume.setPersonalInformation(pi);
        }
        pi.setFirstName(request.getFirstName());
        pi.setMiddleName(request.getMiddleName());
        pi.setLastName(request.getLastName());
        pi.setEmail(request.getEmail());
        pi.setPhone(request.getPhone());
        pi.setAddress(request.getEmail());

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
}
