package com.resumegenerator.output.Controllers;

import com.resumegenerator.output.Models.Resume;
import com.resumegenerator.output.Requests.CreateResumeRequest;
import com.resumegenerator.output.Services.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ResumeController {

    private final ResumeService resumeService;

    @Autowired
    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    // ---------------- CRUD Endpoints ----------------

    @PostMapping("/resumes")
    public ResponseEntity<Resume> createResume(@RequestBody CreateResumeRequest request) {
        Resume resume = resumeService.createResume(request);
        return new ResponseEntity<>(resume, HttpStatus.CREATED);
    }

    @GetMapping("/resumes")
    public ResponseEntity<List<Resume>> getAllResumes() {
        return ResponseEntity.ok(resumeService.getAllResumes());
    }

    @PutMapping("/resumes/{resumeId}")
    public ResponseEntity<Resume> updateResumebyID(@PathVariable Long resumeId, @RequestBody CreateResumeRequest request) {
        Resume updated = resumeService.updateResumebyID(resumeId, request);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/resumes/{resumeId}")
    public ResponseEntity<Void> deleteResume(@PathVariable Long resumeId) {
        resumeService.deleteResume(resumeId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/resumes")
    public ResponseEntity<Void> deleteAllResumes() {
        resumeService.deleteAllResumes();
        return ResponseEntity.noContent().build();
    }

    // ---------------- PDF Generation ----------------

    @PostMapping(value = "/resumes/generate", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> generateResumePdf(@RequestBody Resume resume) throws Exception {
        // Pass the full resume object to the service
        byte[] pdfBytes = resumeService.generateResumePdf(resume);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("inline").filename("resume.pdf").build());

        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }
}
