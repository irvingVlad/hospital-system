package com.hospital.repository;

import com.hospital.entity.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MedicamentoRepository extends JpaRepository<Medicamento, UUID> {
}
