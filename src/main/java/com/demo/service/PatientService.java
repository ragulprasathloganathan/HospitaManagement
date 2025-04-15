package com.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.model.MedicalHistory;
import com.demo.model.Patient;
import com.demo.model.User;
import com.demo.repository.MedicalHistoryRepository;
import com.demo.repository.PatientRepository;
import com.demo.repository.UserRepository;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private MedicalHistoryRepository medicalRepo;

    public Patient addPatient(Patient patient) {
        // Save user
        User user = patient.getUser();
        user = userRepo.save(user);
        patient.setUser(user);

        // Link patient to each medical history before saving
        List<MedicalHistory> histories = patient.getMedicalHistory();
        if (histories != null) {
            for (MedicalHistory history : histories) {
                history.setPatient(patient); // set reference to patient
            }
        }

        // Save patient along with medical history (cascade handled)
        patient.setMedicalHistory(histories);
        Patient savedPatient = patientRepo.save(patient);

        return savedPatient;
    }
    

    public Optional<Patient> getPatientById(int id) {
        return patientRepo.findById(id);
    }
}
