package com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.repository;

import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
