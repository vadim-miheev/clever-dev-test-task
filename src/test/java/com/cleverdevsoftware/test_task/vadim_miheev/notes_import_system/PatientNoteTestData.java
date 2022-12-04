package com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system;

import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.model.PatientNote;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class PatientNoteTestData {
    public final static List<PatientNote> CASE_1_NOTES = List.of(
            new PatientNote(
                    null,
                    LocalDateTime.of(2019, 8, 4, 4, 20, 14),
                    LocalDateTime.of(2019, 8, 4, 4, 24, 14),
                    new User(null, "p.smith"),
                    new User(null, "p.smith"),
                    "Labore deserunt ad reprehenderit id ullamco. Excepteur quis pariatur sit ullamco ipsum irure ex culpa do occaecat. Eu magna Lorem nisi eiusmod dolore ipsum duis laboris excepteur reprehenderit.\r\n",
                    null),
            new PatientNote(
                    null,
                    LocalDateTime.of(2019, 6, 15, 2, 15, 19),
                    LocalDateTime.of(2019, 6, 15, 3, 15, 19),
                    new User(null, "p.shymko"),
                    new User(null, "p.shymko"),
                    "Et nostrud velit aliqua Lorem qui duis dolore labore consequat. Aliquip Lorem laboris cupidatat laboris ad dolor minim do proident nisi et do in laboris. Amet Lorem id adipisicing nulla voluptate exercitation excepteur. Id excepteur voluptate qui cupidatat ut velit magna ex officia fugiat deserunt aliquip. Labore ex Lorem dolor ullamco magna commodo.\r\n",
                    null),
            new PatientNote(
                    null,
                    LocalDateTime.of(2016, 6, 3, 11, 19, 27),
                    LocalDateTime.of(2022, 10, 3, 1, 49, 1),
                    new User(null, "a.kolosov"),
                    new User(null, "a.kolosov"),
                    "Tempor id ad sit dolore nulla aute aliquip dolor cillum excepteur elit ut minim pariatur. Commodo eiusmod consectetur est tempor cupidatat sunt veniam labore cupidatat veniam. Laborum elit veniam eu deserunt velit consectetur ad sit amet ex. Anim cillum fugiat dolor ad deserunt pariatur. Fugiat esse tempor occaecat mollit ut ut minim nulla labore commodo proident aliquip.\r\n",
                    null)
    );
}
