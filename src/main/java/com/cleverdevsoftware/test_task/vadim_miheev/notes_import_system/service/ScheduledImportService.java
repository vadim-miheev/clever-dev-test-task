package com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.service;

import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.dto.PatientNoteTo;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.dto.PatientTo;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.model.ImportStatistics;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
@Slf4j
public class ScheduledImportService {

    private WebClient webClient;
    private PatientService patientService;
    private ObjectMapper objectMapper;

    @Scheduled(cron = "0 15 */2 * * ?")
    public void start() {
        log.info("Import started");

        ImportStatistics statistics = new ImportStatistics();

        try {
            List<PatientTo> patientsFromOldSystem = Objects.requireNonNull(getPatientsMono().block());

            patientService.getMapWithPatientsForImport(patientsFromOldSystem).forEach(
                    (patient, mappedPatientsFromOldSystem) -> mappedPatientsFromOldSystem.forEach(
                            patientFromOldSystem -> patientService.updatePatientNotes(patient,
                                    getPatientNotesMono(patientFromOldSystem).block(), statistics)
                    )
            );

            log.info("Import finished");
            log.info("Notes was created: {}; Notes was updated: {}; Users was created: {}; Datetime duplicate errors: {}",
                    statistics.notesWasCreated.get(), statistics.notesWasUpdated.get(),
                    statistics.usersWasCreated.get(), statistics.datetimeDuplicatesErrors.get());

        } catch (WebClientRequestException e) {
            log.error("Request to {} failed. Reason: {}", e.getUri(), e.getMessage());
        }
    }

    private Mono<List<PatientTo>> getPatientsMono() {
        return webClient
                .get()
                .uri("/clients")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }

    private Mono<List<PatientNoteTo>> getPatientNotesMono(PatientTo patient) {
        @Getter
        @AllArgsConstructor
        class NotesRequestBody {
            private String agency;
            private String clientGuid;
            private String dateFrom;
            private String dateTo;
        }

        String bodyValue;
        try {
            bodyValue = objectMapper.writeValueAsString(new NotesRequestBody(
                    patient.getAgency(),
                    patient.getGuid(),
                    "1970-01-01",
                    "3000-01-01"));
        } catch (JsonProcessingException e) {
            log.error("Wrong client data", e);
            throw new RuntimeException();
        }

        return webClient
                .post()
                .uri("/notes")
                .bodyValue(bodyValue)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }
}
