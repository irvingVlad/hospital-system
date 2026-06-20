package com.hospital.repository;

import com.hospital.entity.Prescripcion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PrescripcionRepository extends JpaRepository<Prescripcion, UUID> {

    List<Prescripcion> findByConsultaId(UUID consultaId);

}
