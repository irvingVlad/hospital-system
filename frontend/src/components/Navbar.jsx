import { Link, useLocation } from 'react-router-dom';

const links = [
  { to: '/pacientes', label: '🧑‍⚕️ Pacientes' },
  { to: '/medicos', label: '👨‍⚕️ Médicos' },
  { to: '/citas', label: '📅 Citas' },
];

function Navbar() {
  const { pathname } = useLocation();

  return (
    <nav style={styles.nav}>
      <span style={styles.brand}>🏥 Hospital System</span>
      <div style={styles.links}>
        {links.map((link) => (
          <Link
            key={link.to}
            to={link.to}
            style={{
              ...styles.link,
              ...(pathname === link.to ? styles.active : {}),
            }}
          >
            {link.label}
          </Link>
        ))}
      </div>
    </nav>
  );
}

const styles = {
  nav: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'space-between',
    padding: '1rem 2rem',
    backgroundColor: '#1a1a2e',
    color: 'white',
  },
  brand: {
    fontSize: '1.2rem',
    fontWeight: 'bold',
    color: 'white',
  },
  links: {
    display: 'flex',
    gap: '1rem',
  },
  link: {
    color: '#a0aec0',
    textDecoration: 'none',
    padding: '0.5rem 1rem',
    borderRadius: '6px',
    transition: 'background 0.2s',
  },
  active: {
    backgroundColor: '#e53e3e',
    color: 'white',
  },
};

export default Navbar;