import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const DeletePatient = () => {
  const [email, setEmail] = useState('');
  const [message, setMessage] = useState('');
  const navigate = useNavigate();

  const handleDelete = async e => {
    e.preventDefault();
    try {
      const response = await fetch(`http://localhost:8080/api/patient/email/${email}`, {
        method: 'DELETE',
      });
      if (response.ok) {
        setMessage('Patient deleted successfully');
      } else {
        setMessage('Failed to delete patient');
      }
      setEmail('');
    } catch (error) {
      console.error('Error deleting patient:', error);
      setMessage('Error deleting patient');
    }
  };

  return (
    <div>
      <h2>Please enter the email of the patient to delete:</h2>
      <form onSubmit={handleDelete}>
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={e => setEmail(e.target.value)}
          required
        />
        <button type="submit">Delete Patient</button>
      </form>
      {message && (
        <div>
          <p>{message}</p>
          <button onClick={() => navigate('/')}>Go to Home</button>
        </div>
      )}
    </div>
  );
};

export default DeletePatient;
