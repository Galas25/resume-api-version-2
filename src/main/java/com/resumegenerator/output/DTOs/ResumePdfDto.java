package com.resumegenerator.output.DTOs;

import com.resumegenerator.output.Models.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumePdfDto {
    private PersonalInformation personalInformation;
    private Skills skills;
    private Education education;
    private ProfessionalSummary professionalSummary;
    private Experience experience;
}
