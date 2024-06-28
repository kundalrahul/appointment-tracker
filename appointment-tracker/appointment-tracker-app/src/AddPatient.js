import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './FormStyles.css'; // Import custom CSS for styling

const AddPatient = () => {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  const handleSubmit = async e => {
    e.preventDefault();
    try {
      // Check if the email already exists
      const existingPatientResponse = await fetch(`http://localhost:8080/api/patient/email/${email}`);
      if (existingPatientResponse.ok) {
        const existingPatient = await existingPatientResponse.json();
        if (existingPatient) {
          setMessage('Email address is already used.');
          return;
        }
      }
      // If email doesn't exist, proceed to add the patient
      const response = await fetch('http://localhost:8080/api/patient', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ name, email }),
      });
      if (response.ok) {
        setMessage('Patient added successfully');
      } else {
        setMessage('Failed to add patient');
      }
      setName('');
      setEmail('');
    } catch (error) {
      console.error('Error adding patient:', error);
      setMessage('Error adding patient');
    }
  };

  return (
    <div className="form-container">
      <h2>Add Patient</h2>
      <form className="patient-form" onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Name"
          value={name}
          onChange={e => setName(e.target.value)}
          required
        />
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={e => setEmail(e.target.value)}
          required
        />
        <button className="submit-button" type="submit">Add Patient</button>
      </form>
      {message && (
        <div className="message-container">
          <p className={`message ${message.includes('Error') ? 'error-message' : ''}`}>
            {message}
          </p>
          <button className="home-button" onClick={() => navigate('/')}>Go to Home</button>
        </div>
      )}
    </div>
  );
};

export default AddPatient;
