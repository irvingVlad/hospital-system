package com.hospital.controller;

import com.hospital.dto.PacienteDTO;
import com.hospital.service.PacienteService;
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
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
@Tag(name = "Pacientes", description = "Operaciones CRUD para la gestion de pacientes")
public class PacienteController {

    private final PacienteService pacienteService;

    @GetMapping
    @Operation(summary = "Listar todos los pacientes")
    public ResponseEntity<List<PacienteDTO>> getAll() {
        return ResponseEntity.ok(pacienteService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un paciente por ID")
    public ResponseEntity<PacienteDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(pacienteService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo paciente")
    public ResponseEntity<PacienteDTO> create(@Valid @RequestBody PacienteDTO dto) {
        PacienteDTO creado = pacienteService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un paciente existente")
    public ResponseEntity<PacienteDTO> update(@PathVariable UUID id, @Valid @RequestBody PacienteDTO dto) {
        return ResponseEntity.ok(pacienteService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un paciente")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        pacienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
