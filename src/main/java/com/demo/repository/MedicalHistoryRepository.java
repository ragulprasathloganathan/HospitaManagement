package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.model.MedicalHistory;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Integer> {
}
