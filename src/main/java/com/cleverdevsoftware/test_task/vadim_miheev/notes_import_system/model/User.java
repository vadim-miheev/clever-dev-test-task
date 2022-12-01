package com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "company_user")
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String login;
}
