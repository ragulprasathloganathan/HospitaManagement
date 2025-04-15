package com.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.demo.model.Patient;
import com.demo.repository.DoctorAppointmentRepository;
import com.demo.service.DoctorAppointmentService;

public class DoctorAppointmentServiceTest {

    @Mock
    private DoctorAppointmentRepository doctorAppointmentRepository;

    @InjectMocks
    private DoctorAppointmentService doctorAppointmentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetPatientsByDoctorId() {
        int doctorId = 1;

        Patient patient1 = new Patient();
        patient1.setId(101);
        patient1.setName("John Doe");

        Patient patient2 = new Patient();
        patient2.setId(102);
        patient2.setName("Jane Smith");

        List<Patient> mockPatients = Arrays.asList(patient1, patient2);

        when(doctorAppointmentRepository.findPatientsByDoctorId(doctorId)).thenReturn(mockPatients);

        List<Patient> result = doctorAppointmentService.getPatientsByDoctorId(doctorId);

        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        verify(doctorAppointmentRepository, times(1)).findPatientsByDoctorId(doctorId);
    }
}
