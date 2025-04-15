package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
}
