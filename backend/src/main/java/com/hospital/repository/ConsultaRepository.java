package com.hospital.repository;

import com.hospital.entity.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ConsultaRepository extends JpaRepository<Consulta, UUID> {

    List<Consulta> findByExpedienteId(UUID expedienteId);

}
