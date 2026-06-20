package com.hospital.service;

import com.hospital.dto.PacienteDTO;
import com.hospital.entity.Paciente;
import com.hospital.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    public List<PacienteDTO> findAll() {
        return pacienteRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PacienteDTO findById(UUID id) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Paciente no encontrado con id: " + id));
        return toDTO(paciente);
    }

    public PacienteDTO create(PacienteDTO dto) {
        Paciente paciente = toEntity(dto);
        paciente.setId(null); // aseguramos que se genere uno nuevo
        Paciente guardado = pacienteRepository.save(paciente);
        return toDTO(guardado);
    }

    public PacienteDTO update(UUID id, PacienteDTO dto) {
        Paciente existente = pacienteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Paciente no encontrado con id: " + id));

        existente.setNombre(dto.getNombre());
        existente.setApellido(dto.getApellido());
        existente.setFechaNacimiento(dto.getFechaNacimiento());
        existente.setTelefono(dto.getTelefono());
        existente.setDireccion(dto.getDireccion());

        return toDTO(pacienteRepository.save(existente));
    }

    public void delete(UUID id) {
        if (!pacienteRepository.existsById(id)) {
            throw new NoSuchElementException("Paciente no encontrado con id: " + id);
        }
        pacienteRepository.deleteById(id);
    }

    // ===== Conversores Entity <-> DTO =====

    private PacienteDTO toDTO(Paciente p) {
        return new PacienteDTO(
                p.getId(),
                p.getNombre(),
                p.getApellido(),
                p.getFechaNacimiento(),
                p.getTelefono(),
                p.getDireccion()
        );
    }

    private Paciente toEntity(PacienteDTO dto) {
        return new Paciente(
                dto.getId(),
                dto.getNombre(),
                dto.getApellido(),
                dto.getFechaNacimiento(),
                dto.getTelefono(),
                dto.getDireccion()
        );
    }
}
