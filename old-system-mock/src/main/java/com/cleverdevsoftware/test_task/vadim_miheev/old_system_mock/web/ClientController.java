package com.cleverdevsoftware.test_task.vadim_miheev.old_system_mock.web;

import com.cleverdevsoftware.test_task.vadim_miheev.old_system_mock.dto.ClientTo;
import com.cleverdevsoftware.test_task.vadim_miheev.old_system_mock.model.Client;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ClientController {

    @GetMapping("/clients")
    public List<ClientTo> getClients () {

        Resource resource = new ClassPathResource("/static/generated-data.json");
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return convertToDto(Arrays.asList(mapper.readValue(resource.getInputStream(), Client[].class)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<ClientTo> convertToDto(List<Client> clients) {
        return clients.stream()
                .map(client -> new ClientTo(
                        client.getAgency(),
                        client.getGuid(),
                        client.getFirstName(),
                        client.getLastName(),
                        client.getStatus(),
                        client.getDob(),
                        client.getCreatedDateTime()
                ))
                .toList();
    }
}
