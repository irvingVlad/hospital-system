package com.hospital.service;

import com.hospital.dto.ConsultaDTO;
import com.hospital.entity.Consulta;
import com.hospital.entity.Expediente;
import com.hospital.entity.Medico;
import com.hospital.repository.ConsultaRepository;
import com.hospital.repository.ExpedienteRepository;
import com.hospital.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final ExpedienteRepository expedienteRepository;
    private final MedicoRepository medicoRepository;

    public List<ConsultaDTO> findByExpediente(UUID expedienteId) {
        return consultaRepository.findByExpedienteId(expedienteId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ConsultaDTO findById(UUID id) {
        return toDTO(getOrThrow(id));
    }

    public ConsultaDTO create(UUID expedienteId, ConsultaDTO dto) {
        Expediente expediente = expedienteRepository.findById(expedienteId)
                .orElseThrow(() -> new NoSuchElementException("Expediente no encontrado con id: " + expedienteId));
        Medico medico = medicoRepository.findById(dto.getMedicoId())
                .orElseThrow(() -> new NoSuchElementException("Medico no encontrado con id: " + dto.getMedicoId()));

        Consulta consulta = new Consulta();
        consulta.setExpediente(expediente);
        consulta.setMedico(medico);
        consulta.setFecha(dto.getFecha() != null ? dto.getFecha() : LocalDateTime.now());
        consulta.setDiagnostico(dto.getDiagnostico());
        consulta.setTratamiento(dto.getTratamiento());
        consulta.setNotas(dto.getNotas());

        return toDTO(consultaRepository.save(consulta));
    }

    public void delete(UUID id) {
        if (!consultaRepository.existsById(id)) {
            throw new NoSuchElementException("Consulta no encontrada con id: " + id);
        }
        consultaRepository.deleteById(id);
    }

    private Consulta getOrThrow(UUID id) {
        return consultaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Consulta no encontrada con id: " + id));
    }

    private ConsultaDTO toDTO(Consulta c) {
        ConsultaDTO dto = new ConsultaDTO();
        dto.setId(c.getId());
        dto.setExpedienteId(c.getExpediente().getId());
        dto.setMedicoId(c.getMedico().getId());
        dto.setMedicoNombreCompleto(c.getMedico().getNombre() + " " + c.getMedico().getApellido());
        dto.setFecha(c.getFecha());
        dto.setDiagnostico(c.getDiagnostico());
        dto.setTratamiento(c.getTratamiento());
        dto.setNotas(c.getNotas());
        return dto;
    }
}
