package com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.model;


import java.util.concurrent.atomic.AtomicInteger;

public class ImportStatistics {
    public AtomicInteger notesWasUpdated = new AtomicInteger(0);
    public AtomicInteger notesWasCreated = new AtomicInteger(0);
    public AtomicInteger usersWasCreated = new AtomicInteger(0);
    public AtomicInteger datetimeDuplicatesErrors = new AtomicInteger(0);
}
