package com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.util;

import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.dto.PatientNoteTo;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.model.Patient;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.model.PatientNote;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.model.User;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.repository.UserRepository;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@UtilityClass
@Slf4j
public class PatientNotesUtil {
    /***
     * @return Have the notes been updated
     */
    public static boolean updateNecessaryPatientNotes(Patient patient, List<PatientNoteTo> notesFromOldSystem, UserRepository userRepository) {
        AtomicBoolean anyUpdated = new AtomicBoolean(false);
        notesFromOldSystem.forEach(oldSystemNote -> {
            List<PatientNote> matchedNotes = patient.getPatientNotes().stream()
                    .filter(note -> note.getCreated().equals(oldSystemNote.getCreatedDateTime()))
                    .toList();

            if (matchedNotes.size() == 1) {
                PatientNote note = matchedNotes.get(0);
                if (note.getLastModified().isBefore(oldSystemNote.getModifiedDateTime())) {
                    note.setNote(oldSystemNote.getComments());
                    note.setLastModified(oldSystemNote.getModifiedDateTime());
                    anyUpdated.set(true);
                }
            } else if(matchedNotes.size() > 1) {
                log.info("Notes found with duplicate creation date");
            } else {
                User user = userRepository.getUserByLogin(oldSystemNote.getLoggedUser())
                        .orElse(new User(null, oldSystemNote.getLoggedUser()));

                patient.getPatientNotes().add(new PatientNote(
                        null,
                        oldSystemNote.getCreatedDateTime(),
                        oldSystemNote.getModifiedDateTime(),
                        user,
                        user,
                        oldSystemNote.getComments(),
                        patient
                ));
                anyUpdated.set(true);
            }
        });
        return anyUpdated.get();
    }
}
