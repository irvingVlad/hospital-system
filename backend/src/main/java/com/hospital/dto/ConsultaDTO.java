package com.hospital.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaDTO {

    private UUID id;

    private UUID expedienteId;

    @NotNull(message = "El medico es obligatorio")
    private UUID medicoId;

    private String medicoNombreCompleto;

    private LocalDateTime fecha;

    private String diagnostico;

    private String tratamiento;

    private String notas;

}
