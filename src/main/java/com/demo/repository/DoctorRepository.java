package com.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.demo.model.Doctor;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    // Custom query methods can be added here if necessary, for example:
    // Optional<Doctor> findByName(String name);
    // List<Doctor> findBySpeciality(String speciality);
}
