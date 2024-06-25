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

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    public Patient saveOrUpdatePatient(Patient patient) {
        return patientRepository.save(patient);
    }

}
