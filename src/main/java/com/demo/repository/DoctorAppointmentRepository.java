package com.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.DoctorAppointment;
import com.demo.model.Patient;

public interface DoctorAppointmentRepository extends JpaRepository<DoctorAppointment, Integer> {
    // Add custom queries if needed
	List<Patient> findPatientsByDoctorId(int doctorId);

}
