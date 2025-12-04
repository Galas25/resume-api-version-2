package com.resumegenerator.output.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "address_id")
    private Long address_id;

    @Column(name = "address")
    private String address;
}
