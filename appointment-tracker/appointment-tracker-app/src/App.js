import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link, useLocation } from 'react-router-dom';
import './FormStyles.css'; // Import custom CSS for styling
import AddPatient from './AddPatient';
import DeletePatient from './DeletePatient';
import UpdatePatient from './UpdatePatient';
import UpdatePatientEmail from './UpdatePatientEmail';

const App = () => {
  const location = useLocation();
  const isHomePage = location.pathname === '/';

  return (
    <div className="app-container">
      {isHomePage && (
        <header className="header">
          <h1 className="app-title">Appointment Tracker App</h1>
          <nav className="nav-links">
            <Link className="nav-link" to="/add">Add Patient</Link>
            <Link className="nav-link" to="/delete">Delete Patient</Link>
            <Link className="nav-link" to="/update-email">Update Patient</Link>
          </nav>
        </header>
      )}
      <Routes>
        <Route path="/add" element={<AddPatient />} />
        <Route path="/delete" element={<DeletePatient />} />
        <Route path="/update-email" element={<UpdatePatientEmail />} />
        <Route path="/update/:email" element={<UpdatePatient />} />
        <Route path="/" element={<div><h2>Welcome to the Appointment Tracker</h2></div>} />
      </Routes>
    </div>
  );
};

const AppWrapper = () => (
  <Router>
    <App />
  </Router>
);

export default AppWrapper;
