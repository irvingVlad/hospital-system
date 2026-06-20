package com.hospital.controller;

import com.hospital.dto.PrescripcionDTO;
import com.hospital.service.PrescripcionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/prescripciones")
@RequiredArgsConstructor
@Tag(name = "Prescripciones", description = "Prescripcion de medicamentos en una consulta")
public class PrescripcionController {

    private final PrescripcionService prescripcionService;

    @GetMapping("/consulta/{consultaId}")
    @Operation(summary = "Listar las prescripciones de una consulta")
    public ResponseEntity<List<PrescripcionDTO>> getByConsulta(@PathVariable UUID consultaId) {
        return ResponseEntity.ok(prescripcionService.findByConsulta(consultaId));
    }

    @PostMapping
    @Operation(summary = "Registrar una nueva prescripcion")
    public ResponseEntity<PrescripcionDTO> create(@Valid @RequestBody PrescripcionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(prescripcionService.create(dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una prescripcion")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        prescripcionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
