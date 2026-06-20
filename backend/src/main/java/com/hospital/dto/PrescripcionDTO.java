package com.hospital.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescripcionDTO {

    private UUID id;

    @NotNull(message = "La consulta es obligatoria")
    private UUID consultaId;

    @NotNull(message = "El medicamento es obligatorio")
    private UUID medicamentoId;

    private String medicamentoNombre;

    private String dosis;

    private String frecuencia;

    private Integer duracionDias;

}
