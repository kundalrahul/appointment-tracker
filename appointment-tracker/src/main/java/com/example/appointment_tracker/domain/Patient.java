package com.example.appointment_tracker.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private Date lastAppointmentDate;

    // Constructors, getters, setters

    public Patient() {
    }

    public Patient(Long id, String name, String email, Date lastAppointmentDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.lastAppointmentDate = lastAppointmentDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getLastAppointmentDate() {
        return lastAppointmentDate;
    }

    public void setLastAppointmentDate(Date lastAppointmentDate) {
        this.lastAppointmentDate = lastAppointmentDate;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", lastAppointmentDate=" + lastAppointmentDate +
                '}';
    }
}
