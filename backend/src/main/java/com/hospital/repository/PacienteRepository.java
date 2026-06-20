package com.hospital.repository;

import com.hospital.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PacienteRepository extends JpaRepository<Paciente, UUID> {
    // JpaRepository ya incluye: findAll(), findById(), save(), deleteById(), etc.
    // Aquí se pueden agregar consultas personalizadas, por ejemplo:
    // List<Paciente> findByApellidoContainingIgnoreCase(String apellido);
}
