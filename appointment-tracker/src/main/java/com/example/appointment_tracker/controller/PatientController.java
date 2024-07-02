package com.example.appointment_tracker.controller;

import com.example.appointment_tracker.domain.Patient;
import com.example.appointment_tracker.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    public Patient createPatient(@RequestBody Patient patient) {
        return patientService.createPatient(patient);
    }

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

    @GetMapping("/check-appointments")
    public ResponseEntity<ByteArrayResource> checkAppointments() {
        try {
            ByteArrayResource resource = patientService.checkAppointments();

            if (resource == null) {
                return ResponseEntity.noContent().build();
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=overdue_appointments.xlsx") // or .docx
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

}
