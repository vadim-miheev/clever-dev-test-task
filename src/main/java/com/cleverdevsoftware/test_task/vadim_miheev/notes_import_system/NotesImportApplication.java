package com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system;

import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.service.ScheduledImportService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class NotesImportApplication {
    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(NotesImportApplication.class, args);
        if (Boolean.parseBoolean(app.getBean(Environment.class).getProperty("app.run-import-after-start", "false"))) {
            app.getBean(ScheduledImportService.class).start();
        }
    }
}
