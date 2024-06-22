package com.example.TicTacToe.Utils;

import com.example.TicTacToe.Authentication.AuthenticationRequest;
import com.example.TicTacToe.Services.AuthenticationService;
import com.example.TicTacToe.Entities.Role;
import com.example.TicTacToe.Repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInitializationRunner implements CommandLineRunner {

    private final AuthenticationService service;
    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        createUserIfNotExists("Luca", "123");
        createUserIfNotExists("Andrei", "123");
        createRoleUSERIfNotExists();
    }

    private void createUserIfNotExists(String username, String rawPassword) {
        AuthenticationRequest newUser = AuthenticationRequest.builder()
                .username(username)
                .password(rawPassword)
                .build();

        service.register(newUser);
    }

    private void createRoleUSERIfNotExists()
    {
        if (roleRepository.findByName("USER").isEmpty())
            roleRepository.save(
                    Role.builder().name("USER").build()
            );
        else
            System.out.println("USER role already exists");
    }
}
