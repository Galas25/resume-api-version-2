package com.resumegenerator.output.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ResumeRequest {
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String skills;
    private String institution;
    private String completionDate;
}
