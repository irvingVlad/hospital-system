package com.hospital.controller;

import com.hospital.dto.ConsultaDTO;
import com.hospital.dto.ExpedienteDTO;
import com.hospital.service.ConsultaService;
import com.hospital.service.ExpedienteService;
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
@RequestMapping("/api/expedientes")
@RequiredArgsConstructor
@Tag(name = "Expedientes", description = "Gestion de expedientes y consultas medicas")
public class ExpedienteController {

    private final ExpedienteService expedienteService;
    private final ConsultaService consultaService;

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Obtener (o crear) el expediente de un paciente")
    public ResponseEntity<ExpedienteDTO> getByPaciente(@PathVariable UUID pacienteId) {
        return ResponseEntity.ok(expedienteService.findByPacienteId(pacienteId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un expediente por ID")
    public ResponseEntity<ExpedienteDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(expedienteService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar antecedentes de un expediente")
    public ResponseEntity<ExpedienteDTO> update(@PathVariable UUID id, @RequestBody ExpedienteDTO dto) {
        return ResponseEntity.ok(expedienteService.update(id, dto));
    }

    // ── Consultas anidadas dentro del expediente ──────────────

    @GetMapping("/{id}/consultas")
    @Operation(summary = "Listar las consultas de un expediente")
    public ResponseEntity<List<ConsultaDTO>> getConsultas(@PathVariable UUID id) {
        return ResponseEntity.ok(consultaService.findByExpediente(id));
    }

    @PostMapping("/{id}/consultas")
    @Operation(summary = "Registrar una nueva consulta en el expediente")
    public ResponseEntity<ConsultaDTO> addConsulta(@PathVariable UUID id, @Valid @RequestBody ConsultaDTO dto) {
        ConsultaDTO creada = consultaService.create(id, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }
}
