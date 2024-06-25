package com.example.appointment_tracker.controller;

import com.example.appointment_tracker.domain.Patient;
import com.example.appointment_tracker.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        Patient createdPatient = patientService.saveOrUpdatePatient(patient);
        return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/appointments")
    public ResponseEntity<Patient> updateLastAppointmentDate(@PathVariable Long id, @RequestBody Patient patient) {
        // Validation and update logic
        Patient updatedPatient = patientService.saveOrUpdatePatient(patient);
        return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
    }

}
