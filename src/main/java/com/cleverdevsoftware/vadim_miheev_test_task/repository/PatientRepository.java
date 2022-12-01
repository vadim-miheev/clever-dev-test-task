package com.cleverdevsoftware.vadim_miheev_test_task.repository;

import com.cleverdevsoftware.vadim_miheev_test_task.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
