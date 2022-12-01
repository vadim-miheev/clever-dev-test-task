package com.cleverdevsoftware.vadim_miheev_test_task.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "patient_profile")
@Getter
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "old_client_guid")
    private String oldClientGuid;

    @Column(name = "status_id", nullable = false)
    private Short statusId;
}
