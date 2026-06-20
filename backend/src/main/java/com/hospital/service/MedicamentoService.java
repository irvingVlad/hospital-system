package com.hospital.service;

import com.hospital.dto.MedicamentoDTO;
import com.hospital.entity.Medicamento;
import com.hospital.repository.MedicamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;

    public List<MedicamentoDTO> findAll() {
        return medicamentoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public MedicamentoDTO findById(UUID id) {
        return toDTO(getOrThrow(id));
    }

    public MedicamentoDTO create(MedicamentoDTO dto) {
        Medicamento medicamento = new Medicamento(null, dto.getNombre(), dto.getDescripcion(), dto.getPresentacion());
        return toDTO(medicamentoRepository.save(medicamento));
    }

    public MedicamentoDTO update(UUID id, MedicamentoDTO dto) {
        Medicamento medicamento = getOrThrow(id);
        medicamento.setNombre(dto.getNombre());
        medicamento.setDescripcion(dto.getDescripcion());
        medicamento.setPresentacion(dto.getPresentacion());
        return toDTO(medicamentoRepository.save(medicamento));
    }

    public void delete(UUID id) {
        if (!medicamentoRepository.existsById(id)) {
            throw new NoSuchElementException("Medicamento no encontrado con id: " + id);
        }
        medicamentoRepository.deleteById(id);
    }

    private Medicamento getOrThrow(UUID id) {
        return medicamentoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Medicamento no encontrado con id: " + id));
    }

    private MedicamentoDTO toDTO(Medicamento m) {
        return new MedicamentoDTO(m.getId(), m.getNombre(), m.getDescripcion(), m.getPresentacion());
    }
}
