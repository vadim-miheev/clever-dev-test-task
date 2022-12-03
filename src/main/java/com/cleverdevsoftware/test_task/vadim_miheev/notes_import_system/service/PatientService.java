package com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.service;

import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.dto.PatientNoteTo;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.dto.PatientTo;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.model.Patient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class PatientService {
    public Map<Patient, List<PatientTo>> getMapWithPatientsForImport(List<PatientTo> patientTos) {
        return null;
    }

    public void updatePatientNotes(Patient patient, List<PatientNoteTo> patientNoteTos) {

    }
}
