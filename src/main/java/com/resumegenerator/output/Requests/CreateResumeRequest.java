package com.resumegenerator.output.Requests;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateResumeRequest {
    @NotBlank
    private String firstName;

    private String middleName;

    @NotBlank
    private String lastName;

    private String suffix;
    private String email;

    @NotBlank
    private String phone;
    @NotBlank
    private String address;

    private String skills;

    private String institution;

    private String completionDate;
    private String professionalSummary;
    private String experience;

}
