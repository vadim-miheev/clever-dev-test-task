package com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.service;

import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.dto.PatientNoteTo;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.dto.PatientTo;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.model.Patient;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.repository.PatientNoteRepository;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.repository.PatientRepository;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.repository.UserRepository;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.util.PatientNotesUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PatientService {
    private PatientRepository patientRepository;
    private PatientNoteRepository patientNoteRepository;
    private UserRepository userRepository;

    public Map<Patient, List<PatientTo>> getMapWithPatientsForImport(List<PatientTo> patientTos) {
        Map<Patient, List<PatientTo>> result = new HashMap<>();
        Map<String, PatientTo> preparedPatientsTos = patientTos.stream()
                .collect(Collectors.toMap(PatientTo::getGuid, p -> p));

        patientRepository.getAllActive().forEach(patient -> Arrays.stream(patient.getOldClientGuid().split(",")).forEach(guid -> {
            if (preparedPatientsTos.containsKey(guid)) {
                result.computeIfAbsent(patient, k -> new ArrayList<>());
                result.get(patient).add(preparedPatientsTos.get(guid));
            }
        }));

        return result;
    }
    @Transactional
    public void updatePatientNotes(Patient patient, List<PatientNoteTo> patientNoteTos) {
        patient.setPatientNotes(patientNoteRepository.getPatientNotesByPatient(patient));
        if(PatientNotesUtil.updateNecessaryPatientNotes(patient, patientNoteTos, userRepository)){
            patientRepository.saveAndFlush(patient);
        }

    }
}
