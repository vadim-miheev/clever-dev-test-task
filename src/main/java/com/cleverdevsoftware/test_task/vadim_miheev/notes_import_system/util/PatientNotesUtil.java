package com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.util;

import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.dto.PatientNoteTo;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.model.Patient;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.repository.UserRepository;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class PatientNotesUtil {
    /***
     * @return Have the notes been updated
     */
    public static boolean updateNecessaryPatientNotes(Patient patient, List<PatientNoteTo> notesFromOldSystem, UserRepository userRepository) {
        return false;
    }
}
