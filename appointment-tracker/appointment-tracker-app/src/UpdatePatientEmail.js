import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const UpdatePatientEmail = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [error, setError] = useState('');

  const handleSearch = async (e) => {
    e.preventDefault();
    try {
      const response = await fetch(`http://localhost:8080/api/patient/email/${email}`);
      if (response.ok) {
        navigate(`/update/${email}`);
      } else {
        setError('Email not found. Please enter a valid email.');
      }
    } catch (error) {
      console.error('Error searching for patient:', error);
      setError('Failed to search for patient. Please try again later.');
    }
  };

  return (
    <div>
      <h2>Update Patient</h2>
      <form onSubmit={handleSearch}>
        <label>
          Enter Patient Email:
          <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required />
        </label>
        <button type="submit">Search</button>
      </form>
      {error && <p style={{ color: 'red' }}>{error}</p>}
    </div>
  );
};

export default UpdatePatientEmail;
