package com.hospital.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "expediente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expediente {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false, unique = true)
    private Paciente paciente;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @Column(columnDefinition = "TEXT")
    private String antecedentes;

}
