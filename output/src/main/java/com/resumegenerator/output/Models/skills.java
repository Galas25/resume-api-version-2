package com.resumegenerator.output.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class skills {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skills_id")
    private Long skills_id;

    @Column(name = "skill", length = 500)
    private String skill;
}
