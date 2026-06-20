package com.hospital.repository;

import com.hospital.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CitaRepository extends JpaRepository<Cita, UUID> {

    List<Cita> findByPacienteId(UUID pacienteId);

    List<Cita> findByMedicoId(UUID medicoId);

}
