package com.hospital.controller;

import com.hospital.dto.MedicamentoDTO;
import com.hospital.service.MedicamentoService;
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
@RequestMapping("/api/medicamentos")
@RequiredArgsConstructor
@Tag(name = "Medicamentos", description = "Catalogo de medicamentos")
public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    @GetMapping
    @Operation(summary = "Listar todos los medicamentos")
    public ResponseEntity<List<MedicamentoDTO>> getAll() {
        return ResponseEntity.ok(medicamentoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un medicamento por ID")
    public ResponseEntity<MedicamentoDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(medicamentoService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo medicamento")
    public ResponseEntity<MedicamentoDTO> create(@Valid @RequestBody MedicamentoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicamentoService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un medicamento existente")
    public ResponseEntity<MedicamentoDTO> update(@PathVariable UUID id, @Valid @RequestBody MedicamentoDTO dto) {
        return ResponseEntity.ok(medicamentoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un medicamento")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        medicamentoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
