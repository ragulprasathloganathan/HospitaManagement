package com.demo.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.Doctor;
import com.demo.model.DoctorAppointment;
import com.demo.model.Patient;
import com.demo.service.DoctorAppointmentService;
import com.demo.service.DoctorService;
import com.demo.service.PatientService;

@RestController
@RequestMapping("/api/appointments")
public class DoctorAppointmentController {

    @Autowired
    private DoctorAppointmentService doctorAppointmentService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/create")
    public DoctorAppointment createAppointment(@RequestParam int patientId, @RequestParam int doctorId, @RequestParam String appointmentTime) {
        // Fetch the existing patient and doctor by IDs
        Optional<Patient> patient = patientService.getPatientById(patientId);
        Doctor doctor = doctorService.getDoctorById(doctorId);

        // Check if patient or doctor is null
        if (patient.isEmpty() || doctor == null) {
            throw new RuntimeException("Patient or Doctor not found");
        }

        // Parse the appointment time to LocalDateTime
        LocalDateTime appointmentDateTime = LocalDateTime.parse(appointmentTime);

        // Create the DoctorAppointment
        DoctorAppointment appointment = new DoctorAppointment(patient.get(), doctor, appointmentDateTime);

        // Save the appointment to the database
        return doctorAppointmentService.saveAppointment(appointment);
    }
    @Autowired
    
    // GET API to fetch all patients by doctor ID, accessible only by doctor role
    @PreAuthorize("hasRole('ROLE_DOCTOR')")
    @GetMapping("/patients")
    public List<Patient> getPatientsByDoctorId(@PathVariable int doctorId) {
        return doctorAppointmentService.getPatientsByDoctorId(doctorId);
    }
}
