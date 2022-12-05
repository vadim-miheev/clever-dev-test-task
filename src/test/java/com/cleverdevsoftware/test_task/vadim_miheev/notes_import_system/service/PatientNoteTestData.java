package com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.service;

import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.model.PatientNote;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.model.User;

import java.time.LocalDateTime;
import java.util.List;

public class PatientNoteTestData {
    public static final Long CASE_1_USER_ID = 16L;
    public static final Long CASE_2_USER_ID = 3L;
    public static final Long CASE_3_USER_ID = 3L;
    public static final  List<PatientNote> CASE_1_NOTES = List.of(
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

    public static List<PatientNote> getDefaultDataForUser20() {
        return List.of(
                new PatientNote(
                        null,
                        LocalDateTime.of(2020, 11, 26, 9, 8, 34),
                        LocalDateTime.of(2020, 11, 26, 9, 8, 34),
                        new User(null, "r.petersen"),
                        new User(null, "r.petersen"),
                        "Incididunt irure id deserunt proident tempor ut ullamco mollit nisi et. Deserunt proident irure consectetur veniam minim minim duis deserunt officia ullamco non deserunt ea cupidatat. Sit ullamco est eiusmod proident id adipisicing sit proident aliqua minim ex ullamco.\n",
                        null),
                new PatientNote(
                        null,
                        LocalDateTime.of(2017, 10, 5, 1, 46, 24),
                        LocalDateTime.of(2022, 9, 18, 3, 56, 6),
                        new User(null, "j.smith"),
                        new User(null, "j.smith"),
                        "Qui consectetur id labore non nulla tempor officia sunt anim exercitation reprehenderit qui velit dolore. Anim in incididunt enim deserunt anim aute elit laborum. Non sit veniam in deserunt consequat eiusmod quis et. Esse deserunt irure dolor fugiat officia sunt aliquip mollit irure nulla occaecat fugiat.\n",
                        null),
                new PatientNote(
                        null,
                        LocalDateTime.of(2017, 5, 6, 2, 57, 19),
                        LocalDateTime.of(2022, 3, 4, 1, 56, 21),
                        new User(null, "a.kolosov"),
                        new User(null, "a.kolosov"),
                        "Tempor ea deserunt est fugiat magna est velit nostrud aute commodo. Nisi aliquip cillum incididunt magna nostrud aliqua magna. Qui esse Lorem Lorem incididunt cupidatat consequat. Reprehenderit adipisicing sit culpa id voluptate elit labore officia laborum elit ex consectetur ex.\n",
                        null),
                new PatientNote(
                        null,
                        LocalDateTime.of(2016, 12, 11, 2, 28, 0),
                        LocalDateTime.of(2016, 12, 11, 2, 28, 0),
                        new User(null, "p.shymko"),
                        new User(null, "p.shymko"),
                        "Lorem labore minim Lorem veniam. Anim dolor cillum eiusmod est do enim ipsum sunt mollit amet nisi dolor est. Dolor consectetur tempor cupidatat nisi mollit aute laboris magna cupidatat ex culpa nulla ut. Amet tempor id irure occaecat occaecat cupidatat commodo et. Labore occaecat occaecat nostrud et ut. Voluptate ut consectetur culpa excepteur consequat esse minim aute non.\n",
                        null)
        );
    }
}
