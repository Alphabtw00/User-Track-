package com.example.usertrack.config;

import com.example.usertrack.entity.Manager;
import com.example.usertrack.repository.ManagerRepository;
import com.example.usertrack.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class CommandLineConfig implements CommandLineRunner {

    private final ManagerRepository managerRepository;

    public CommandLineConfig(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Manager> managers = Arrays.asList(
                new Manager(UUID.randomUUID(), "Manager One", "manager1@example.com", false),
                new Manager(UUID.randomUUID(), "Manager Two", "manager2@example.com", true),
                new Manager(UUID.randomUUID(), "Manager Three", "manager3@example.com", true),
                new Manager(UUID.randomUUID(), "Manager Four", "manager4@example.com", true),
                new Manager(UUID.randomUUID(), "Manager Five", "manager5@example.com", true),
                new Manager(UUID.randomUUID(), "Manager Six", "manager6@example.com", true),
                new Manager(UUID.randomUUID(), "Manager Seven", "manager7@example.com", true),
                new Manager(UUID.randomUUID(), "Manager Eight", "manager8@example.com", true),
                new Manager(UUID.randomUUID(), "Manager Nine", "manager9@example.com", true),
                new Manager(UUID.randomUUID(), "Manager Ten", "manager10@example.com", true)
        );

        managerRepository.saveAll(managers);
    }
}
