package com.hospital.controller;

import com.hospital.dto.MedicoDTO;
import com.hospital.service.MedicoService;
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
@RequestMapping("/api/medicos")
@RequiredArgsConstructor
@Tag(name = "Medicos", description = "Operaciones CRUD para la gestion de medicos")
public class MedicoController {

    private final MedicoService medicoService;

    @GetMapping
    @Operation(summary = "Listar todos los medicos")
    public ResponseEntity<List<MedicoDTO>> getAll() {
        return ResponseEntity.ok(medicoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un medico por ID")
    public ResponseEntity<MedicoDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(medicoService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo medico")
    public ResponseEntity<MedicoDTO> create(@Valid @RequestBody MedicoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(medicoService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un medico existente")
    public ResponseEntity<MedicoDTO> update(@PathVariable UUID id, @Valid @RequestBody MedicoDTO dto) {
        return ResponseEntity.ok(medicoService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un medico")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        medicoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
