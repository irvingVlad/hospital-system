# 🏥 Sistema de Gestión Hospitalaria

> Proyecto Final — Desarrollo de Aplicaciones Web  
> Universidad de El Salvador — Ingeniería en Desarrollo de Software  
> Tutor: Ing. Victoria Castro

---

## 📋 Descripción del Proyecto

Sistema web full-stack para la gestión hospitalaria que permite controlar pacientes, citas médicas y expedientes clínicos. Desarrollado con arquitectura Cliente-Servidor desacoplada usando Spring Boot en el backend y React en el frontend.

**Funciones principales:**
- Registro y gestión de pacientes
- Agendamiento y control de citas médicas
- Manejo de expedientes y consultas médicas
- Gestión de médicos y especialidades
- Prescripción de medicamentos

---

## 👥 Integrantes

| Nombre Completo | Carnet |
|----------------|--------|
| | |
| | |
| | |
| | |
| | |

---

## 🗄️ Diagrama Entidad-Relación

> Insertar imagen del DER aquí

```
database/DER.png  ←  colocar la imagen en esta carpeta
```

---

## 🛠️ Stack Tecnológico

| Capa | Tecnología |
|------|-----------|
| Backend | Spring Boot 3.x (Java) |
| Base de Datos | PostgreSQL |
| Frontend | React + JavaScript |
| Contenedores | Docker + Docker Compose |
| ORM | JPA / Hibernate |
| API Docs | Swagger / OpenAPI |

---

## 🚀 Manual de Despliegue

### Requisitos previos
- Docker Desktop instalado y corriendo
- Git

### Pasos

**1. Clonar el repositorio**
```bash
git clone https://github.com/TU_USUARIO/hospital-system.git
cd hospital-system
```

**2. Levantar todo el sistema con un solo comando**
```bash
docker-compose up --build
```

**3. Acceder a los servicios**

| Servicio | URL |
|---------|-----|
| Frontend | http://localhost:5173 |
| Backend API | http://localhost:8080/api |
| Swagger UI | http://localhost:8080/swagger-ui.html |
| PostgreSQL | localhost:5432 |

**4. Para detener el sistema**
```bash
docker-compose down
```

---

## 📡 Endpoints del Backend

### Pacientes
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/pacientes` | Listar todos |
| GET | `/api/pacientes/{id}` | Obtener por ID |
| POST | `/api/pacientes` | Crear paciente |
| PUT | `/api/pacientes/{id}` | Actualizar |
| DELETE | `/api/pacientes/{id}` | Eliminar |

### Médicos
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/medicos` | Listar todos |
| GET | `/api/medicos/{id}` | Obtener por ID |
| POST | `/api/medicos` | Crear médico |
| PUT | `/api/medicos/{id}` | Actualizar |
| DELETE | `/api/medicos/{id}` | Eliminar |

### Citas
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/citas` | Listar todas |
| GET | `/api/citas/{id}` | Obtener por ID |
| GET | `/api/citas/paciente/{id}` | Citas de un paciente |
| POST | `/api/citas` | Crear cita |
| PUT | `/api/citas/{id}` | Actualizar |
| DELETE | `/api/citas/{id}` | Cancelar |

### Expedientes
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/expedientes/{pacienteId}` | Expediente del paciente |
| GET | `/api/expedientes/{id}/consultas` | Consultas del expediente |
| POST | `/api/expedientes/{id}/consultas` | Registrar consulta |

---

## 📸 Evidencias de Funcionamiento

### Swagger UI
> _(Agregar capturas aquí)_

### Vistas del Sistema
> _(Agregar capturas aquí)_

---

## 📁 Estructura del Repositorio

```
/
├── backend/          # Spring Boot — API REST
├── frontend/         # React — SPA
├── database/         # Scripts SQL + DER
├── docker-compose.yml
└── README.md
```
