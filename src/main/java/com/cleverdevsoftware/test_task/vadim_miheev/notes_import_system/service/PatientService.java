package com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.service;

import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.dto.PatientNoteTo;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.dto.PatientTo;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.model.ImportStatistics;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.model.Patient;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.model.PatientNote;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.model.User;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.repository.PatientRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class PatientService {
    private PatientRepository patientRepository;
    private UserService userService;

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
    public void updatePatientNotes(Patient patient, List<PatientNoteTo> patientNoteTos, ImportStatistics statistics) {
        patient = patientRepository.getWithNotes(patient.getId()).orElseThrow();
        if(updateNecessaryPatientNotes(patient, patientNoteTos, statistics)){
            patientRepository.saveAndFlush(patient);
        }
    }

    /***
     * @return patient notes have been modified
     */
    private boolean updateNecessaryPatientNotes(Patient patient, List<PatientNoteTo> notesFromOldSystem, ImportStatistics statistics) {
        AtomicBoolean notesHaveBeenModified = new AtomicBoolean(false);

        notesFromOldSystem.forEach(oldSystemNote -> {
            List<PatientNote> matchedNotes = patient.getPatientNotes().stream()
                    .filter(note -> note.getCreated().equals(oldSystemNote.getCreatedDateTime()))
                    .toList();

            if (matchedNotes.size() == 1) {
                PatientNote note = matchedNotes.get(0);
                if (note.getLastModified().isBefore(oldSystemNote.getModifiedDateTime())) {
                    note.setNote(oldSystemNote.getComments());
                    note.setLastModified(oldSystemNote.getModifiedDateTime());
                    if (!note.getLastModifiedBy().getLogin().equals(oldSystemNote.getLoggedUser())) {
                        note.setLastModifiedBy(userService.getOrCreateByLogin(oldSystemNote.getLoggedUser(), statistics));
                    }

                    statistics.notesWasUpdated.incrementAndGet();
                    notesHaveBeenModified.set(true);
                }
            } else if(matchedNotes.size() > 1) {
                log.error(String.format("Patient notes with following ids have the same creation datetime and cannot be updated: %s",
                        String.join(", ", matchedNotes.stream().map(PatientNote::getId).map(String::valueOf).toList())));
            } else {

                User user = userService.getOrCreateByLogin(oldSystemNote.getLoggedUser(), statistics);

                patient.getPatientNotes().add(new PatientNote(
                        null,
                        oldSystemNote.getCreatedDateTime(),
                        oldSystemNote.getModifiedDateTime(),
                        user,
                        user,
                        oldSystemNote.getComments(),
                        patient
                ));
                statistics.notesWasCreated.incrementAndGet();
                notesHaveBeenModified.set(true);
            }
        });
        return notesHaveBeenModified.get();
    }
}
