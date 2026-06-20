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
public class CitaDTO {

    private UUID id;

    @NotNull(message = "El paciente es obligatorio")
    private UUID pacienteId;

    // Campo de solo lectura, util para el frontend (evita una peticion extra)
    private String pacienteNombreCompleto;

    @NotNull(message = "El medico es obligatorio")
    private UUID medicoId;

    private String medicoNombreCompleto;

    @NotNull(message = "La fecha y hora son obligatorias")
    private LocalDateTime fechaHora;

    private String estado;

    private String motivo;

}
