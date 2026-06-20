package com.hospital.service;

import com.hospital.dto.ExpedienteDTO;
import com.hospital.entity.Expediente;
import com.hospital.entity.Paciente;
import com.hospital.repository.ExpedienteRepository;
import com.hospital.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExpedienteService {

    private final ExpedienteRepository expedienteRepository;
    private final PacienteRepository pacienteRepository;

    /**
     * Obtiene el expediente de un paciente. Si no existe, lo crea automaticamente
     * (todo paciente debe tener expediente segun el DER, relacion 1:1).
     */
    public ExpedienteDTO findByPacienteId(UUID pacienteId) {
        return expedienteRepository.findByPacienteId(pacienteId)
                .map(this::toDTO)
                .orElseGet(() -> crearExpedienteParaPaciente(pacienteId));
    }

    public ExpedienteDTO findById(UUID id) {
        return toDTO(getOrThrow(id));
    }

    public ExpedienteDTO update(UUID id, ExpedienteDTO dto) {
        Expediente expediente = getOrThrow(id);
        expediente.setAntecedentes(dto.getAntecedentes());
        return toDTO(expedienteRepository.save(expediente));
    }

    private ExpedienteDTO crearExpedienteParaPaciente(UUID pacienteId) {
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new NoSuchElementException("Paciente no encontrado con id: " + pacienteId));

        Expediente nuevo = new Expediente();
        nuevo.setPaciente(paciente);
        nuevo.setFechaCreacion(LocalDate.now());
        nuevo.setAntecedentes("");

        return toDTO(expedienteRepository.save(nuevo));
    }

    private Expediente getOrThrow(UUID id) {
        return expedienteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Expediente no encontrado con id: " + id));
    }

    private ExpedienteDTO toDTO(Expediente e) {
        ExpedienteDTO dto = new ExpedienteDTO();
        dto.setId(e.getId());
        dto.setPacienteId(e.getPaciente().getId());
        dto.setPacienteNombreCompleto(e.getPaciente().getNombre() + " " + e.getPaciente().getApellido());
        dto.setFechaCreacion(e.getFechaCreacion());
        dto.setAntecedentes(e.getAntecedentes());
        return dto;
    }
}
