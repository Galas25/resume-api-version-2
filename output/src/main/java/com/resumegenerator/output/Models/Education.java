package com.resumegenerator.output.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Education {
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    @JsonBackReference
    private Resume resume;

    @Id
    private Long id;

    private String institution;

    @Column (nullable = true)
    private String completionDate;



}
