package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.DoctorAppointment;
import com.demo.model.Patient;
import com.demo.repository.DoctorAppointmentRepository;

@Service
public class DoctorAppointmentService {

    @Autowired
    private DoctorAppointmentRepository doctorAppointmentRepository;

    // Method to save the appointment
    public DoctorAppointment saveAppointment(DoctorAppointment doctorAppointment) {
        return doctorAppointmentRepository.save(doctorAppointment);
    }

    // You can add other methods to fetch appointments, etc. if needed.
 // Method to get all patients by doctor ID
    public List<Patient> getPatientsByDoctorId(int doctorId) {
        return doctorAppointmentRepository.findPatientsByDoctorId(doctorId);
    }
}
