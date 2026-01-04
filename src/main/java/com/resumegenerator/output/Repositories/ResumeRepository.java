package com.resumegenerator.output.Repositories;

import com.resumegenerator.output.Models.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

}
