package com.resumegenerator.output.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class contactNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contactnum_id")
    private Long contactnum_id;

    @Column(name = "contact_number")
    private Long contact_number;
}
