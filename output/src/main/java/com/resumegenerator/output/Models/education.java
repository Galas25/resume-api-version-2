package com.resumegenerator.output.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_id")
    private Long education_id;

    @Column (name = "institution")
    private String institution;

    @Column (name = "year_completion")
    private Date year_completion;

}
