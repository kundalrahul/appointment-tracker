package com.example.appointment_tracker.controller;

import com.example.appointment_tracker.domain.Patient;
import com.example.appointment_tracker.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

/*    @GetMapping
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }*/

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return patientService.createPatient(patient);
    }

/*
    @PutMapping("/{id}/appointments")
    public ResponseEntity<Patient> updateLastAppointmentDate(@PathVariable Long id, @RequestBody Patient patient) {
        // Validation and update logic
        Patient updatedPatient = patientService.saveOrUpdatePatient(patient);
        return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
    }
*/

    @DeleteMapping("/email/{email}")
    public void deletePatientByEmail(@PathVariable String email) {
        patientService.deletePatientByEmail(email);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getPatientByEmail(@PathVariable String email) {
        try {
            Optional<Patient> patient = patientService.getPatientByEmail(email);
            return patient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving patient details");
        }
    }
    @PutMapping("/email/{email}")
    public ResponseEntity<?> updatePatientDetails(@PathVariable String email, @RequestBody Patient updatedPatient) {
        try {
            Patient updated = patientService.updatePatientDetails(email, updatedPatient);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating patient details");
        }
    }

}
