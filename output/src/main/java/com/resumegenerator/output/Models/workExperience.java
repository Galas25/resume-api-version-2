package com.resumegenerator.output.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class workExperience {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "workexp_id")
    private Long workexp_id;

    @Column(name = "summary", length = 500)
    private String summary;

    @Column(name = "experience", length = 500)
    private String experience;
}
