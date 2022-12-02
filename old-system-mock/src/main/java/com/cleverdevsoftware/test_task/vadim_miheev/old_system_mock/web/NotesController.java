package com.cleverdevsoftware.test_task.vadim_miheev.old_system_mock.web;

import com.cleverdevsoftware.test_task.vadim_miheev.old_system_mock.dto.ClientNoteTo;
import com.cleverdevsoftware.test_task.vadim_miheev.old_system_mock.model.Client;
import com.cleverdevsoftware.test_task.vadim_miheev.old_system_mock.model.ClientNote;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class NotesController {

    @Setter
    @Getter
    static class NotesRequest {
        private String agency;
        private String clientGuid;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate dateFrom;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate dateTo;
    }

    @PostMapping("/notes")
    public List<ClientNoteTo> getClientNotes(@RequestBody NotesRequest requestTo) {

        Resource resource = new ClassPathResource("/static/generated-data.json");
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            List<ClientNote> clientNotes = lookForClient(
                    Arrays.asList(mapper.readValue(resource.getInputStream(), Client[].class)),
                    client -> client.getAgency().equals(requestTo.getAgency()) && client.getGuid().equals(requestTo.getClientGuid())).getNotes();

            return filterAndConvertNotesToDto(
                    clientNotes,
                    clientNote -> clientNote.getModifiedDateTime().toLocalDate().isAfter(requestTo.getDateFrom())
                            && clientNote.getModifiedDateTime().toLocalDate().isBefore(requestTo.getDateTo()),
                    requestTo.getClientGuid());
        } catch (IOException|NoSuchElementException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<ClientNoteTo> filterAndConvertNotesToDto(List<ClientNote> notes, Predicate<ClientNote> notesFilter, String clientGuid) {
        return notes.stream()
                .filter(notesFilter)
                .map(note -> new ClientNoteTo(
                        note.getComments(),
                        note.getGuid(),
                        note.getModifiedDateTime(),
                        clientGuid,
                        note.getDatetime(),
                        note.getCreatedDateTime(),
                        note.getLoggedUser()
                )).toList();
    }

    private Client lookForClient(List<Client> clients, Predicate<Client> clientFilter) {
        return clients.stream()
                .filter(clientFilter)
                .findAny().orElseThrow();
    }
}