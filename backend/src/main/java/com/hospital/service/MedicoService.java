package com.hospital.service;

import com.hospital.dto.MedicoDTO;
import com.hospital.entity.Medico;
import com.hospital.repository.MedicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicoService {

    private final MedicoRepository medicoRepository;

    public List<MedicoDTO> findAll() {
        return medicoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public MedicoDTO findById(UUID id) {
        return toDTO(getOrThrow(id));
    }

    public MedicoDTO create(MedicoDTO dto) {
        Medico medico = new Medico(null, dto.getNombre(), dto.getApellido(),
                dto.getEspecialidad(), dto.getNumeroColegiado());
        return toDTO(medicoRepository.save(medico));
    }

    public MedicoDTO update(UUID id, MedicoDTO dto) {
        Medico medico = getOrThrow(id);
        medico.setNombre(dto.getNombre());
        medico.setApellido(dto.getApellido());
        medico.setEspecialidad(dto.getEspecialidad());
        medico.setNumeroColegiado(dto.getNumeroColegiado());
        return toDTO(medicoRepository.save(medico));
    }

    public void delete(UUID id) {
        if (!medicoRepository.existsById(id)) {
            throw new NoSuchElementException("Medico no encontrado con id: " + id);
        }
        medicoRepository.deleteById(id);
    }

    private Medico getOrThrow(UUID id) {
        return medicoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Medico no encontrado con id: " + id));
    }

    private MedicoDTO toDTO(Medico m) {
        return new MedicoDTO(m.getId(), m.getNombre(), m.getApellido(),
                m.getEspecialidad(), m.getNumeroColegiado());
    }
}
