package com.example.appointment_tracker.service;

import com.example.appointment_tracker.domain.Patient;
import com.example.appointment_tracker.repository.PatientRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public ByteArrayResource checkAppointments() throws IOException {
        List<Patient> patients = patientRepository.findAll();
        LocalDate currentDate = LocalDate.now();

        List<Patient> overduePatients = patients.stream()
                .filter(patient -> patient.getLastAppointmentDate() != null)
                .filter(patient -> {
                    LocalDate appointmentDate = patient.getLastAppointmentDate().toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                    return ChronoUnit.DAYS.between(appointmentDate, currentDate) > 45;
                })
                .collect(Collectors.toList());

        if (overduePatients.isEmpty()) {
            return null;
        }

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            createExcelFile(overduePatients, outputStream);
            return new ByteArrayResource(outputStream.toByteArray());
        }
    }

    private void createExcelFile(List<Patient> overduePatients, ByteArrayOutputStream outputStream) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Overdue Appointments");
            Row headerRow = sheet.createRow(0);

            Cell headerCell1 = headerRow.createCell(0);
            headerCell1.setCellValue("Name");

            Cell headerCell2 = headerRow.createCell(1);
            headerCell2.setCellValue("Email");

            int rowNum = 1;
            for (Patient patient : overduePatients) {
                Row row = sheet.createRow(rowNum++);

                Cell cell1 = row.createCell(0);
                cell1.setCellValue(patient.getName());

                Cell cell2 = row.createCell(1);
                cell2.setCellValue(patient.getEmail());
            }

            workbook.write(outputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
