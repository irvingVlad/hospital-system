package com.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpedienteDTO {

    private UUID id;

    private UUID pacienteId;

    private String pacienteNombreCompleto;

    private LocalDate fechaCreacion;

    private String antecedentes;

}
