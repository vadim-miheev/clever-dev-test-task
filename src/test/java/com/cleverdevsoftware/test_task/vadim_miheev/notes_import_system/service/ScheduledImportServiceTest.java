package com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.service;

import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.model.Patient;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.model.PatientNote;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.model.User;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.repository.PatientRepository;
import lombok.extern.slf4j.Slf4j;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.time.LocalDateTime;
import java.util.List;

import static com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.service.PatientNoteTestData.*;
import static java.nio.charset.StandardCharsets.UTF_8;

@SpringBootTest
@Slf4j
class ScheduledImportServiceTest {
    public static MockWebServer mockBackEnd;

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @TestConfiguration
    public static class WebClientTestConfiguration {
        @Primary
        @Bean
        public WebClient getWebClient() {
            return WebClient.create(String.format("http://localhost:%s", mockBackEnd.getPort()));
        }
    }

    @Autowired
    ScheduledImportService scheduledImportService;

    @Autowired
    PatientRepository patientRepository;

    /*** create 3 new notes for 1 user */
    @Test
    @Transactional
    @Sql({"/clean-up.sql", "/schema.sql" ,"/data.sql"})
    void case1_notesCreating() {
        // Mock old system responses
        addNextMockResponseBodyFromJsonFile("/test_data/import_system/clients/case1.json");
        addNextMockResponseBodyFromJsonFile("/test_data/import_system/notes/case1.json");

        // Import
        scheduledImportService.start();

        // Check results
        Patient patient = patientRepository.getWithNotes(CASE_1_USER_ID).orElseThrow();

        assert patient.getPatientNotes().size() == 3;

        Assertions.assertThat(patient.getPatientNotes()).usingRecursiveComparison()
                .ignoringFields("id", "patient", "createdBy.id", "lastModifiedBy.id")
                .isEqualTo(CASE_1_NOTES);
    }

    @Test
    @Transactional
    @Sql({"/clean-up.sql", "/schema.sql" ,"/test_data/import_system/default-state.sql"})
    void case2_notesTextUpdating() {
        // Mock old system responses
        addNextMockResponseBodyFromJsonFile("/test_data/import_system/clients/case2.json");
        addNextMockResponseBodyFromJsonFile("/test_data/import_system/notes/case2.json");

        // Import
        scheduledImportService.start();

        // Check results
        Patient patient = patientRepository.getWithNotes(CASE_2_USER_ID).orElseThrow();

        assert patient.getPatientNotes().size() == 4;

        List<PatientNote> referenceData = getDefaultDataForUser20();
        referenceData.get(1).setNote("Text after update");
        referenceData.get(1).setLastModified(LocalDateTime.of(2022, 9 ,18, 4, 57, 6));

        Assertions.assertThat(patient.getPatientNotes()).usingRecursiveComparison()
                .ignoringFields("id", "patient", "createdBy.id", "lastModifiedBy.id")
                .isEqualTo(referenceData);
    }

    @Test
    @Transactional()
    @Sql({"/clean-up.sql", "/schema.sql", "/test_data/import_system/default-state.sql"})
    void case3_notesTextAndUserUpdating() {
        // Mock old system responses
        addNextMockResponseBodyFromJsonFile("/test_data/import_system/clients/case3.json");
        addNextMockResponseBodyFromJsonFile("/test_data/import_system/notes/case3.json");

        // Import
        scheduledImportService.start();

        // Check results
        Patient patient = patientRepository.getWithNotes(CASE_3_USER_ID).orElseThrow();

        assert patient.getPatientNotes().size() == 4;

        List<PatientNote> referenceData = getDefaultDataForUser20();
        referenceData.get(2).setNote("New text");
        referenceData.get(2).setLastModified(LocalDateTime.of(2022, 3 ,5, 2, 53, 20));
        referenceData.get(2).setLastModifiedBy(new User(null, "new.user.test"));

        Assertions.assertThat(patient.getPatientNotes()).usingRecursiveComparison()
                .ignoringFields("id", "patient", "createdBy.id", "lastModifiedBy.id")
                .isEqualTo(referenceData);
    }

    private void addNextMockResponseBodyFromJsonFile(String path) {
        try (Reader reader = new InputStreamReader(new ClassPathResource(path).getInputStream(), UTF_8)) {
            mockBackEnd.enqueue(new MockResponse()
                    .setBody(FileCopyUtils.copyToString(reader))
                    .addHeader("Content-Type", "application/json"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}