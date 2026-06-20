package com.hospital.controller;

import com.hospital.dto.ConsultaDTO;
import com.hospital.service.ConsultaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/consultas")
@RequiredArgsConstructor
@Tag(name = "Consultas", description = "Operaciones sobre consultas medicas individuales")
public class ConsultaController {

    private final ConsultaService consultaService;

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una consulta por ID")
    public ResponseEntity<ConsultaDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(consultaService.findById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una consulta")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        consultaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
