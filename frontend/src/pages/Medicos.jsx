import { useState, useEffect } from 'react';
import api from '../api/axios';

function Medicos() {
  const [medicos, setMedicos] = useState([]);
  const [form, setForm] = useState({
    nombre: '', apellido: '', especialidad: '', numeroColegiado: ''
  });
  const [editId, setEditId] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    cargarMedicos();
  }, []);

  const cargarMedicos = async () => {
    try {
      const res = await api.get('/medicos');
      setMedicos(res.data);
    } catch {
      setError('Error al cargar médicos');
    }
  };

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (editId) {
        await api.put(`/medicos/${editId}`, form);
      } else {
        await api.post('/medicos', form);
      }
      setForm({ nombre: '', apellido: '', especialidad: '', numeroColegiado: '' });
      setEditId(null);
      cargarMedicos();
    } catch {
      setError('Error al guardar médico');
    }
  };

  const handleEdit = (m) => {
    setForm({
      nombre: m.nombre,
      apellido: m.apellido,
      especialidad: m.especialidad,
      numeroColegiado: m.numeroColegiado,
    });
    setEditId(m.id);
  };

  const handleDelete = async (id) => {
    if (!confirm('¿Eliminar este médico?')) return;
    try {
      await api.delete(`/medicos/${id}`);
      cargarMedicos();
    } catch {
      setError('Error al eliminar médico');
    }
  };

  return (
    <div>
      <h2 style={styles.title}>👨‍⚕️ Gestión de Médicos</h2>

      {error && <p style={styles.error}>{error}</p>}

      {/* Formulario */}
      <form onSubmit={handleSubmit} style={styles.form}>
        <h3>{editId ? 'Editar Médico' : 'Nuevo Médico'}</h3>
        <div style={styles.grid}>
          <input name="nombre" placeholder="Nombre" value={form.nombre}
            onChange={handleChange} required style={styles.input} />
          <input name="apellido" placeholder="Apellido" value={form.apellido}
            onChange={handleChange} required style={styles.input} />
          <input name="especialidad" placeholder="Especialidad" value={form.especialidad}
            onChange={handleChange} required style={styles.input} />
          <input name="numeroColegiado" placeholder="Número de colegiado" value={form.numeroColegiado}
            onChange={handleChange} required style={styles.input} />
        </div>
        <div style={{ display: 'flex', gap: '1rem', marginTop: '1rem' }}>
          <button type="submit" style={styles.btnPrimary}>
            {editId ? 'Actualizar' : 'Guardar'}
          </button>
          {editId && (
            <button type="button" style={styles.btnSecondary}
              onClick={() => { setEditId(null); setForm({ nombre: '', apellido: '', especialidad: '', numeroColegiado: '' }); }}>
              Cancelar
            </button>
          )}
        </div>
      </form>

      {/* Tabla */}
      <table style={styles.table}>
        <thead>
          <tr style={styles.thead}>
            <th style={styles.th}>Nombre</th>
            <th style={styles.th}>Apellido</th>
            <th style={styles.th}>Especialidad</th>
            <th style={styles.th}>N° Colegiado</th>
            <th style={styles.th}>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {medicos.map((m) => (
            <tr key={m.id} style={styles.tr}>
              <td style={styles.td}>{m.nombre}</td>
              <td style={styles.td}>{m.apellido}</td>
              <td style={styles.td}>{m.especialidad}</td>
              <td style={styles.td}>{m.numeroColegiado}</td>
              <td style={styles.td}>
                <button onClick={() => handleEdit(m)} style={styles.btnEdit}>Editar</button>
                <button onClick={() => handleDelete(m.id)} style={styles.btnDelete}>Eliminar</button>
              </td>
            </tr>
          ))}
          {medicos.length === 0 && (
            <tr><td colSpan={5} style={{ textAlign: 'center', padding: '1rem', color: '#888' }}>
              No hay médicos registrados
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
  input: { padding: '0.6rem', borderRadius: '6px', border: '1px solid #ccc', fontSize: '0.95rem', width: '100%' },
  btnPrimary: { padding: '0.6rem 1.5rem', backgroundColor: '#e53e3e', color: 'white', border: 'none', borderRadius: '6px', cursor: 'pointer' },
  btnSecondary: { padding: '0.6rem 1.5rem', backgroundColor: '#718096', color: 'white', border: 'none', borderRadius: '6px', cursor: 'pointer' },
  table: { width: '100%', borderCollapse: 'collapse' },
  thead: { backgroundColor: '#1a1a2e' },
  th: { padding: '0.75rem 1rem', color: 'white', textAlign: 'left' },
  tr: { borderBottom: '1px solid #eee' },
  td: { padding: '0.75rem 1rem' },
  btnEdit: { marginRight: '0.5rem', padding: '0.3rem 0.8rem', backgroundColor: '#3182ce', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' },
  btnDelete: { padding: '0.3rem 0.8rem', backgroundColor: '#e53e3e', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer' },
};

export default Medicos;