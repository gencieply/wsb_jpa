package com.jpacourse.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PatientTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String telephoneNumber;
    private String email;
    private String patientNumber;
    private LocalDate dateOfBirth;
    private LocalDate registrationDate;
    private List<VisitTO> visits;

    public static class VisitTO {
        private LocalDateTime time;
        private String doctorFirstName;
        private String doctorLastName;
        private List<String> treatmentTypes;

        // Getters and setters for VisitTO
        public LocalDateTime getTime() {
            return time;
        }

        public void setTime(LocalDateTime time) {
            this.time = time;
        }

        public String getDoctorFirstName() {
            return doctorFirstName;
        }

        public void setDoctorFirstName(String doctorFirstName) {
            this.doctorFirstName = doctorFirstName;
        }

        public String getDoctorLastName() {
            return doctorLastName;
        }

        public void setDoctorLastName(String doctorLastName) {
            this.doctorLastName = doctorLastName;
        }

        public List<String> getTreatmentTypes() {
            return treatmentTypes;
        }

        public void setTreatmentTypes(List<String> treatmentTypes) {
            this.treatmentTypes = treatmentTypes;
        }
    }

    // Getters and setters for PatientTO
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPatientNumber() {
        return patientNumber;
    }

    public void setPatientNumber(String patientNumber) {
        this.patientNumber = patientNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<VisitTO> getVisits() {
        return visits;
    }

    public void setVisits(List<VisitTO> visits) {
        this.visits = visits;
    }
}

