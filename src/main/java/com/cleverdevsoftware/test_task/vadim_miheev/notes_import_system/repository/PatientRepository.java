package com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.repository;

import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("SELECT p FROM Patient p WHERE p.statusId IN (200, 210, 230)")
    Set<Patient> getAllActive();

    @Query("SELECT p FROM Patient p LEFT JOIN FETCH p.patientNotes WHERE p.id=:id")
    Optional<Patient> getWithNotes(Long id);
}
