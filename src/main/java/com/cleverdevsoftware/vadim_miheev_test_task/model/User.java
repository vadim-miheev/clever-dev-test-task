package com.cleverdevsoftware.vadim_miheev_test_task.model;

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
