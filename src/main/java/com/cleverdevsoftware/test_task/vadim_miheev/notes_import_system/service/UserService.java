package com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.service;

import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.model.ImportStatistics;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.model.User;
import com.cleverdevsoftware.test_task.vadim_miheev.notes_import_system.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;

    public User getOrCreateByLoginAndUpdateStatistics(String login, ImportStatistics statistics) {
        User user = userRepository.getUserByLogin(login).orElse(new User(null, login));
        if (user.getId() == null) {
            user = userRepository.save(user);
            statistics.usersWasCreated.incrementAndGet();
        }
        return user;
    }
}
