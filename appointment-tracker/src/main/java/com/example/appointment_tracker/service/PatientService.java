package com.example.appointment_tracker.service;

import com.example.appointment_tracker.domain.Patient;
import com.example.appointment_tracker.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

/*    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }*/

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public Optional<Patient> findPatientByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    public void deletePatientByEmail(String email) {
        Optional<Patient> patient = patientRepository.findByEmail(email);
        patient.ifPresent(patientRepository::delete);
    }

    public Optional<Patient> getPatientByEmail(String email) {
        return patientRepository.findByEmail(email);
    }

    public Patient updatePatientDetails(String email, Patient updatedPatient) {
        Optional<Patient> patientOptional = patientRepository.findByEmail(email);

        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            // Ensure email cannot be updated
            updatedPatient.setEmail(patient.getEmail());
            // Update other fields
            patient.setName(updatedPatient.getName());
            patient.setLastAppointmentDate(updatedPatient.getLastAppointmentDate());
            return patientRepository.save(patient);
        } else {
            throw new RuntimeException("Patient not found with email: " + email);
        }
    }

}
