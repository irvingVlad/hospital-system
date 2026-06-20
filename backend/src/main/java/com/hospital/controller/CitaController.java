package com.hospital.controller;

import com.hospital.dto.CitaDTO;
import com.hospital.service.CitaService;
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
@RequestMapping("/api/citas")
@RequiredArgsConstructor
@Tag(name = "Citas", description = "Operaciones CRUD para la gestion de citas medicas")
public class CitaController {

    private final CitaService citaService;

    @GetMapping
    @Operation(summary = "Listar todas las citas")
    public ResponseEntity<List<CitaDTO>> getAll() {
        return ResponseEntity.ok(citaService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una cita por ID")
    public ResponseEntity<CitaDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(citaService.findById(id));
    }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Listar las citas de un paciente especifico")
    public ResponseEntity<List<CitaDTO>> getByPaciente(@PathVariable UUID pacienteId) {
        return ResponseEntity.ok(citaService.findByPaciente(pacienteId));
    }

    @PostMapping
    @Operation(summary = "Crear una nueva cita")
    public ResponseEntity<CitaDTO> create(@Valid @RequestBody CitaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(citaService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una cita existente")
    public ResponseEntity<CitaDTO> update(@PathVariable UUID id, @Valid @RequestBody CitaDTO dto) {
        return ResponseEntity.ok(citaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancelar/eliminar una cita")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        citaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
