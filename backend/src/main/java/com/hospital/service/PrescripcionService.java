package com.hospital.service;

import com.hospital.dto.PrescripcionDTO;
import com.hospital.entity.Consulta;
import com.hospital.entity.Medicamento;
import com.hospital.entity.Prescripcion;
import com.hospital.repository.ConsultaRepository;
import com.hospital.repository.MedicamentoRepository;
import com.hospital.repository.PrescripcionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PrescripcionService {

    private final PrescripcionRepository prescripcionRepository;
    private final ConsultaRepository consultaRepository;
    private final MedicamentoRepository medicamentoRepository;

    public List<PrescripcionDTO> findByConsulta(UUID consultaId) {
        return prescripcionRepository.findByConsultaId(consultaId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PrescripcionDTO create(PrescripcionDTO dto) {
        Consulta consulta = consultaRepository.findById(dto.getConsultaId())
                .orElseThrow(() -> new NoSuchElementException("Consulta no encontrada con id: " + dto.getConsultaId()));
        Medicamento medicamento = medicamentoRepository.findById(dto.getMedicamentoId())
                .orElseThrow(() -> new NoSuchElementException("Medicamento no encontrado con id: " + dto.getMedicamentoId()));

        Prescripcion prescripcion = new Prescripcion();
        prescripcion.setConsulta(consulta);
        prescripcion.setMedicamento(medicamento);
        prescripcion.setDosis(dto.getDosis());
        prescripcion.setFrecuencia(dto.getFrecuencia());
        prescripcion.setDuracionDias(dto.getDuracionDias());

        return toDTO(prescripcionRepository.save(prescripcion));
    }

    public void delete(UUID id) {
        if (!prescripcionRepository.existsById(id)) {
            throw new NoSuchElementException("Prescripcion no encontrada con id: " + id);
        }
        prescripcionRepository.deleteById(id);
    }

    private PrescripcionDTO toDTO(Prescripcion p) {
        PrescripcionDTO dto = new PrescripcionDTO();
        dto.setId(p.getId());
        dto.setConsultaId(p.getConsulta().getId());
        dto.setMedicamentoId(p.getMedicamento().getId());
        dto.setMedicamentoNombre(p.getMedicamento().getNombre());
        dto.setDosis(p.getDosis());
        dto.setFrecuencia(p.getFrecuencia());
        dto.setDuracionDias(p.getDuracionDias());
        return dto;
    }
}
