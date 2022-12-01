package com.cleverdevsoftware.vadim_miheev_test_task.repository;

import com.cleverdevsoftware.vadim_miheev_test_task.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
