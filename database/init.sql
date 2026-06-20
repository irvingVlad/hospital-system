-- ================================================
-- Sistema de Gestión Hospitalaria
-- Script de inicialización de base de datos
-- ================================================

CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Nota: la entidad USUARIO/autenticacion se omitio del backend porque
-- el proyecto ya no requiere JWT segun la rubrica actualizada.

-- MÉDICOS
CREATE TABLE IF NOT EXISTS medico (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    especialidad VARCHAR(100) NOT NULL,
    numero_colegiado VARCHAR(50) UNIQUE NOT NULL
);

-- PACIENTES
CREATE TABLE IF NOT EXISTS paciente (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE,
    telefono VARCHAR(20),
    direccion TEXT
);

-- CITAS (relación N entre PACIENTE y MEDICO)
CREATE TABLE IF NOT EXISTS cita (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    paciente_id UUID NOT NULL REFERENCES paciente(id),
    medico_id UUID NOT NULL REFERENCES medico(id),
    fecha_hora TIMESTAMP NOT NULL,
    estado VARCHAR(20) DEFAULT 'PENDIENTE' CHECK (estado IN ('PENDIENTE','CONFIRMADA','CANCELADA','COMPLETADA')),
    motivo TEXT
);

-- EXPEDIENTES (1:1 con PACIENTE)
CREATE TABLE IF NOT EXISTS expediente (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    paciente_id UUID UNIQUE NOT NULL REFERENCES paciente(id),
    fecha_creacion DATE DEFAULT CURRENT_DATE,
    antecedentes TEXT
);

-- CONSULTAS (N:1 con EXPEDIENTE, N:1 con MEDICO)
CREATE TABLE IF NOT EXISTS consulta (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    expediente_id UUID NOT NULL REFERENCES expediente(id),
    medico_id UUID NOT NULL REFERENCES medico(id),
    fecha TIMESTAMP DEFAULT NOW(),
    diagnostico TEXT,
    tratamiento TEXT,
    notas TEXT
);

-- MEDICAMENTOS
CREATE TABLE IF NOT EXISTS medicamento (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    presentacion VARCHAR(100)
);

-- PRESCRIPCIONES (tabla N:M entre CONSULTA y MEDICAMENTO)
CREATE TABLE IF NOT EXISTS prescripcion (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    consulta_id UUID NOT NULL REFERENCES consulta(id),
    medicamento_id UUID NOT NULL REFERENCES medicamento(id),
    dosis VARCHAR(100),
    frecuencia VARCHAR(100),
    duracion_dias INT
);

-- ================================================
-- DATOS DE PRUEBA
-- ================================================

INSERT INTO medicamento (nombre, descripcion, presentacion) VALUES
  ('Paracetamol', 'Analgésico y antipirético', 'Tabletas 500mg'),
  ('Ibuprofeno', 'Antiinflamatorio no esteroideo', 'Tabletas 400mg'),
  ('Amoxicilina', 'Antibiótico de amplio espectro', 'Cápsulas 500mg');
