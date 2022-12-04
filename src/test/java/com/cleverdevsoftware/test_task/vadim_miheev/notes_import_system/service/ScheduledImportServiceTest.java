package com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.service;

import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.PatientNoteTestData;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.model.Patient;
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
import org.springframework.core.io.Resource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;

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

    /*** create 3 new notes */
    @Test
    @Transactional
    @Sql({"/clean-up.sql", "/schema.sql" ,"/data.sql"})
    void case1() {
        // Mock old system responses
        mockBackEnd.enqueue(new MockResponse()
                .setBody(resourceAsString(new ClassPathResource("/test_data/import_system/clients/case1.json")))
                .addHeader("Content-Type", "application/json"));

        mockBackEnd.enqueue(new MockResponse()
                .setBody(resourceAsString(new ClassPathResource("/test_data/import_system/notes/case1.json")))
                .addHeader("Content-Type", "application/json"));

        // Import
        scheduledImportService.start();

        // Check results
        Patient patient = patientRepository.getWithNotes(16L).orElseThrow();

        assert patient.getPatientNotes().size() == 3;

        Assertions.assertThat(patient.getPatientNotes()).usingRecursiveComparison()
                .ignoringFields("id", "patient", "createdBy.id", "lastModifiedBy.id")
                .isEqualTo(PatientNoteTestData.CASE_1_NOTES);
    }

    private static String resourceAsString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}