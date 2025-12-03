package com.resumegenerator.output.Models;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Builder
@Entity
@Table(name = "Resume")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="resumeId", nullable = false, updatable = false)
    private Long resumeId;

    @Column(name = "firstName", nullable = false, updatable = true)
    private String firstName;

    @Column(name = "lastName", nullable = false, updatable = true)
    private String lastName;

    @Column(name= "email", nullable = false, updatable = true)
    private String email;

    @Column(name = "phone", nullable = false, updatable = true)
    private String phone;



    private LocalDateTime createdAt;
    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
