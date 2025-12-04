package com.resumegenerator.output.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class email {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id")
    private Long email_id;

    @Column(name = "email")
    private String email;
}
