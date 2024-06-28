import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import moment from 'moment';
import './FormStyles.css'; // Import custom CSS for styling

const UpdatePatient = () => {
  const { email } = useParams();
  const navigate = useNavigate();
  const [patient, setPatient] = useState(null);
  const [name, setName] = useState('');
  const [lastAppointmentDate, setLastAppointmentDate] = useState('');

  useEffect(() => {
    const fetchPatient = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/patient/email/${email}`);
        if (response.ok) {
          const data = await response.json();
          setPatient(data);
          setName(data.name);
          setLastAppointmentDate(data.lastAppointmentDate);
        } else {
          console.error('Failed to fetch patient details');
        }
      } catch (error) {
        console.error('Error fetching patient details:', error);
      }
    };
    fetchPatient();
  }, [email]);

  const handleUpdate = async (e) => {
    e.preventDefault();
    try {
      const formattedDate = moment(lastAppointmentDate).add(1, 'day').format('YYYY-MM-DD');

      const response = await fetch(`http://localhost:8080/api/patient/email/${email}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ name, lastAppointmentDate: formattedDate }),
      });
      if (response.ok) {
        console.log('Patient updated successfully');
        navigate('/');
      } else {
        console.error('Failed to update patient');
      }
    } catch (error) {
      console.error('Error updating patient:', error);
    }
  };

  if (!patient) {
    return <div className="loading">Loading...</div>;
  }

  return (
    <div className="form-container">
      <h2>Update Patient</h2>
      <form className="patient-form" onSubmit={handleUpdate}>
        <label>
          Name:
          <input type="text" value={name} onChange={(e) => setName(e.target.value)} required />
        </label>
        <label>
          Last Appointment Date:
          <input
            type="date"
            value={lastAppointmentDate}
            onChange={(e) => setLastAppointmentDate(e.target.value)}
            required
          />
        </label>
        <button className="submit-button" type="submit">Update</button>
      </form>
    </div>
  );
};

export default UpdatePatient;
