package com.hospital.repository;

import com.hospital.entity.Expediente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ExpedienteRepository extends JpaRepository<Expediente, UUID> {

    Optional<Expediente> findByPacienteId(UUID pacienteId);

}
