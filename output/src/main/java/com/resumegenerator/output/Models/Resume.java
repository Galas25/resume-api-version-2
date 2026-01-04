package com.resumegenerator.output.Models;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "Resume")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long resumeId;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "resume", optional = true)
    @JsonManagedReference
    private PersonalInformation PersonalInformation;

    @OneToOne(cascade =  CascadeType.ALL, mappedBy = "resume", optional = true)
    @JsonManagedReference
    private Education education;

    @OneToOne(cascade =  CascadeType.ALL, mappedBy = "resume", optional = true)
    @JsonManagedReference
    private Skills skills;

    private LocalDateTime createdAt;
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    //One to many java connection: (MUST)
}
