import { useState, useEffect } from 'react';
import api from '../api/axios';

function Citas() {
  const [citas, setCitas] = useState([]);
  const [pacientes, setPacientes] = useState([]);
  const [medicos, setMedicos] = useState([]);
  const [form, setForm] = useState({
    pacienteId: '', medicoId: '', fechaHora: '', estado: 'PENDIENTE', motivo: ''
  });
  const [editId, setEditId] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    cargarDatos();
  }, []);

  const cargarDatos = async () => {
    try {
      const [citasRes, pacientesRes, medicosRes] = await Promise.all([
        api.get('/citas'),
        api.get('/pacientes'),
        api.get('/medicos'),
      ]);
      setCitas(citasRes.data);
      setPacientes(pacientesRes.data);
      setMedicos(medicosRes.data);
    } catch {
      setError('Error al cargar datos');
    }
  };

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editId) {
        await api.put(`/citas/${editId}`, form);
      } else {
        await api.post('/citas', form);
      }
      setForm({ pacienteId: '', medicoId: '', fechaHora: '', estado: 'PENDIENTE', motivo: '' });
      setEditId(null);
      cargarDatos();
    } catch {
      setError('Error al guardar cita');
    }
  };

  const handleEdit = (c) => {
    setForm({
      pacienteId: c.pacienteId,
      medicoId: c.medicoId,
      fechaHora: c.fechaHora ? c.fechaHora.slice(0, 16) : '',
      estado: c.estado,
      motivo: c.motivo || '',
    });
    setEditId(c.id);
  };

  const handleDelete = async (id) => {
    if (!confirm('¿Eliminar esta cita?')) return;
    try {
      await api.delete(`/citas/${id}`);
      cargarDatos();
    } catch {
      setError('Error al eliminar cita');
    }
  };

  const estadoColor = (estado) => {
    const colores = {
      PENDIENTE: '#d69e2e',
      CONFIRMADA: '#38a169',
      CANCELADA: '#e53e3e',
      COMPLETADA: '#3182ce',
    };
    return colores[estado] || '#718096';
  };

  return (
    <div>
      <h2 style={styles.title}>📅 Gestión de Citas</h2>

      {error && <p style={styles.error}>{error}</p>}

      {/* Formulario */}
      <form onSubmit={handleSubmit} style={styles.form}>
        <h3>{editId ? 'Editar Cita' : 'Nueva Cita'}</h3>
        <div style={styles.grid}>
          <div>
            <label style={styles.label}>Paciente</label>
            <select name="pacienteId" value={form.pacienteId}
              onChange={handleChange} required style={styles.input}>
              <option value="">-- Seleccionar paciente --</option>
              {pacientes.map((p) => (
                <option key={p.id} value={p.id}>
                  {p.nombre} {p.apellido}
                </option>
              ))}
            </select>
          </div>
          <div>
            <label style={styles.label}>Médico</label>
            <select name="medicoId" value={form.medicoId}
              onChange={handleChange} required style={styles.input}>
              <option value="">-- Seleccionar médico --</option>
              {medicos.map((m) => (
                <option key={m.id} value={m.id}>
                  {m.nombre} {m.apellido} — {m.especialidad}
                </option>
              ))}
            </select>
          </div>
          <div>
            <label style={styles.label}>Fecha y Hora</label>
            <input name="fechaHora" type="datetime-local" value={form.fechaHora}
              onChange={handleChange} required style={styles.input} />
          </div>
          <div>
            <label style={styles.label}>Estado</label>
            <select name="estado" value={form.estado}
              onChange={handleChange} style={styles.input}>
              <option value="PENDIENTE">Pendiente</option>
              <option value="CONFIRMADA">Confirmada</option>
              <option value="CANCELADA">Cancelada</option>
              <option value="COMPLETADA">Completada</option>
            </select>
          </div>
          <div style={{ gridColumn: 'span 2' }}>
            <label style={styles.label}>Motivo</label>
            <input name="motivo" placeholder="Motivo de la cita" value={form.motivo}
              onChange={handleChange} style={styles.input} />
          </div>
        </div>
        <div style={{ display: 'flex', gap: '1rem', marginTop: '1rem' }}>
          <button type="submit" style={styles.btnPrimary}>
            {editId ? 'Actualizar' : 'Guardar'}
          </button>
          {editId && (
            <button type="button" style={styles.btnSecondary}
              onClick={() => { setEditId(null); setForm({ pacienteId: '', medicoId: '', fechaHora: '', estado: 'PENDIENTE', motivo: '' }); }}>
              Cancelar
            </button>
          )}
        </div>
      </form>

      {/* Tabla */}
      <table style={styles.table}>
        <thead>
          <tr style={styles.thead}>
            <th style={styles.th}>Paciente</th>
            <th style={styles.th}>Médico</th>
            <th style={styles.th}>Fecha y Hora</th>
            <th style={styles.th}>Estado</th>
            <th style={styles.th}>Motivo</th>
            <th style={styles.th}>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {citas.map((c) => (
            <tr key={c.id} style={styles.tr}>
              <td style={styles.td}>{c.pacienteNombreCompleto}</td>
              <td style={styles.td}>{c.medicoNombreCompleto}</td>
              <td style={styles.td}>{c.fechaHora ? c.fechaHora.replace('T', ' ').slice(0, 16) : '—'}</td>
              <td style={styles.td}>
                <span style={{ ...styles.badge, backgroundColor: estadoColor(c.estado) }}>
                  {c.estado}
                </span>
              </td>
              <td style={styles.td}>{c.motivo || '—'}</td>
              <td style={styles.td}>
                <button onClick={() => handleEdit(c)} style={styles.btnEdit}>Editar</button>
                <button onClick={() => handleDelete(c.id)} style={styles.btnDelete}>Eliminar</button>
              </td>
            </tr>
          ))}
          {citas.length === 0 && (
            <tr><td colSpan={6} style={{ textAlign: 'center', padding: '1rem', color: '#888' }}>
              No hay citas registradas
            </td></tr>
          )}
        </tbody>
      </table>
    </div>
  );
}

const styles = {
  title: { marginBottom: '1.5rem', color: '#1a1a2e' },
  error: { color: 'red', marginBottom: '1rem' },
  form: { background: '#f7f7f7', padding: '1.5rem', borderRadius: '8px', marginBottom: '2rem' },
  grid: { display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1rem' },
  label: { display: 'block', marginBottom: '0.3rem', fontSize: '0.9rem', color: '#4a5568' },
  input: { padding: '0.6rem', borderRadius: '6px', border: '1px solid #ccc', fontSize: '0.95rem', width: '100%' },
  btnPrimary: { padding: '0.6rem 1.5rem', backgroundColor: '#e53e3e', color: 'white', border: 'none', borderRadius: '6px', cursor: 'pointer' },
  btnSecondary: { padding: '0.6rem 1.5rem', backgroundColor: '#718096', color: 'white', border: 'none', borderRadius: '6px', cursor: 'pointer' },
  table: { width: '100%', borderCollapse: 'collapse' },
  thead: { backgroundColor: '#1a1a2e' },
  th: { padding: '0.75rem 1rem', color: 'white', textAlign: 'left' },
  tr: { borderBottom: '1px solid #eee' },
  td: { padding: '0.75rem 1rem' },
  badge: { padding: '0.2rem 0.6rem', borderRadius: '12px', color: 'white', fontSize: '0.8rem', fontWeight: 'bold' },
  btnEdit: { marginRight: '0.5rem', padding: '0.3rem 0.8rem', backgroundColor: '#3182ce', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' },
  btnDelete: { padding: '0.3rem 0.8rem', backgroundColor: '#e53e3e', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' },
};

export default Citas;