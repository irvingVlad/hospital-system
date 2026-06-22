import { useState, useEffect } from 'react';
import api from '../api/axios';

function Pacientes() {
  const [pacientes, setPacientes] = useState([]);
  const [form, setForm] = useState({
    nombre: '', apellido: '', fechaNacimiento: '', telefono: '', direccion: ''
});
const [editId, setEditId] = useState(null);
const [error, setError] = useState('');

useEffect(() => {
    cargarPacientes();
}, []);

const cargarPacientes = async () => {
    try {
    const res = await api.get('/pacientes');
    setPacientes(res.data);
    } catch {
    setError('Error al cargar pacientes');
    }
};

const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
};

const handleSubmit = async (e) => {
    e.preventDefault();
    try {
    if (editId) {
        await api.put(`/pacientes/${editId}`, form);
    } else {
        await api.post('/pacientes', form);
    }
    setForm({ nombre: '', apellido: '', fechaNacimiento: '', telefono: '', direccion: '' });
    setEditId(null);
    cargarPacientes();
    } catch {
    setError('Error al guardar paciente');
    }
};

const handleEdit = (p) => {
    setForm({
    nombre: p.nombre,
    apellido: p.apellido,
    fechaNacimiento: p.fechaNacimiento || '',
    telefono: p.telefono || '',
    direccion: p.direccion || ''
    });
    setEditId(p.id);
};

const handleDelete = async (id) => {
    if (!confirm('¿Eliminar este paciente?')) return;
    try {
    await api.delete(`/pacientes/${id}`);
    cargarPacientes();
    } catch {
    setError('Error al eliminar paciente');
    }
};

return (
    <div>
    <h2 style={styles.title}>🧑‍⚕️ Gestión de Pacientes</h2>

    {error && <p style={styles.error}>{error}</p>}

    {/* Formulario */}
    <form onSubmit={handleSubmit} style={styles.form}>
        <h3>{editId ? 'Editar Paciente' : 'Nuevo Paciente'}</h3>
        <div style={styles.grid}>
        <input name="nombre" placeholder="Nombre" value={form.nombre}
            onChange={handleChange} required style={styles.input} />
        <input name="apellido" placeholder="Apellido" value={form.apellido}
            onChange={handleChange} required style={styles.input} />
        <input name="fechaNacimiento" type="date" placeholder="Fecha de nacimiento"
            value={form.fechaNacimiento} onChange={handleChange} style={styles.input} />
        <input name="telefono" placeholder="Teléfono" value={form.telefono}
            onChange={handleChange} style={styles.input} />
        <input name="direccion" placeholder="Dirección" value={form.direccion}
            onChange={handleChange} style={{ ...styles.input, gridColumn: 'span 2' }} />
        </div>
        <div style={{ display: 'flex', gap: '1rem', marginTop: '1rem' }}>
        <button type="submit" style={styles.btnPrimary}>
            {editId ? 'Actualizar' : 'Guardar'}
        </button>
        {editId && (
            <button type="button" style={styles.btnSecondary}
            onClick={() => { setEditId(null); setForm({ nombre: '', apellido: '', fechaNacimiento: '', telefono: '', direccion: '' }); }}>
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
            <th style={styles.th}>Fecha Nac.</th>
            <th style={styles.th}>Teléfono</th>
            <th style={styles.th}>Dirección</th>
            <th style={styles.th}>Acciones</th>
        </tr>
        </thead>
        <tbody>
        {pacientes.map((p) => (
            <tr key={p.id} style={styles.tr}>
            <td style={styles.td}>{p.nombre}</td>
            <td style={styles.td}>{p.apellido}</td>
            <td style={styles.td}>{p.fechaNacimiento || '—'}</td>
            <td style={styles.td}>{p.telefono || '—'}</td>
            <td style={styles.td}>{p.direccion || '—'}</td>
            <td style={styles.td}>
                <button onClick={() => handleEdit(p)} style={styles.btnEdit}>Editar</button>
                <button onClick={() => handleDelete(p.id)} style={styles.btnDelete}>Eliminar</button>
            </td>
            </tr>
        ))}
        {pacientes.length === 0 && (
            <tr><td colSpan={6} style={{ textAlign: 'center', padding: '1rem', color: '#888' }}>
            No hay pacientes registrados
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

export default Pacientes;