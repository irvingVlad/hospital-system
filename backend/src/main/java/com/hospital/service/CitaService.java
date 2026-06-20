package com.hospital.service;

import com.hospital.dto.CitaDTO;
import com.hospital.entity.Cita;
import com.hospital.entity.Medico;
import com.hospital.entity.Paciente;
import com.hospital.repository.CitaRepository;
import com.hospital.repository.MedicoRepository;
import com.hospital.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CitaService {

    private final CitaRepository citaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public List<CitaDTO> findAll() {
        return citaRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CitaDTO findById(UUID id) {
        return toDTO(getOrThrow(id));
    }

    public List<CitaDTO> findByPaciente(UUID pacienteId) {
        return citaRepository.findByPacienteId(pacienteId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CitaDTO create(CitaDTO dto) {
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new NoSuchElementException("Paciente no encontrado con id: " + dto.getPacienteId()));
        Medico medico = medicoRepository.findById(dto.getMedicoId())
                .orElseThrow(() -> new NoSuchElementException("Medico no encontrado con id: " + dto.getMedicoId()));

        Cita cita = new Cita();
        cita.setPaciente(paciente);
        cita.setMedico(medico);
        cita.setFechaHora(dto.getFechaHora());
        cita.setEstado(dto.getEstado() != null ? dto.getEstado() : "PENDIENTE");
        cita.setMotivo(dto.getMotivo());

        return toDTO(citaRepository.save(cita));
    }

    public CitaDTO update(UUID id, CitaDTO dto) {
        Cita cita = getOrThrow(id);

        if (dto.getPacienteId() != null) {
            Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                    .orElseThrow(() -> new NoSuchElementException("Paciente no encontrado con id: " + dto.getPacienteId()));
            cita.setPaciente(paciente);
        }
        if (dto.getMedicoId() != null) {
            Medico medico = medicoRepository.findById(dto.getMedicoId())
                    .orElseThrow(() -> new NoSuchElementException("Medico no encontrado con id: " + dto.getMedicoId()));
            cita.setMedico(medico);
        }

        cita.setFechaHora(dto.getFechaHora());
        cita.setEstado(dto.getEstado());
        cita.setMotivo(dto.getMotivo());

        return toDTO(citaRepository.save(cita));
    }

    public void delete(UUID id) {
        if (!citaRepository.existsById(id)) {
            throw new NoSuchElementException("Cita no encontrada con id: " + id);
        }
        citaRepository.deleteById(id);
    }

    private Cita getOrThrow(UUID id) {
        return citaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cita no encontrada con id: " + id));
    }

    private CitaDTO toDTO(Cita c) {
        CitaDTO dto = new CitaDTO();
        dto.setId(c.getId());
        dto.setPacienteId(c.getPaciente().getId());
        dto.setPacienteNombreCompleto(c.getPaciente().getNombre() + " " + c.getPaciente().getApellido());
        dto.setMedicoId(c.getMedico().getId());
        dto.setMedicoNombreCompleto(c.getMedico().getNombre() + " " + c.getMedico().getApellido());
        dto.setFechaHora(c.getFechaHora());
        dto.setEstado(c.getEstado());
        dto.setMotivo(c.getMotivo());
        return dto;
    }
}
