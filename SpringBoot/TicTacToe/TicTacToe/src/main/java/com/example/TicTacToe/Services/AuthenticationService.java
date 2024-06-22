package com.example.TicTacToe.Services;

import com.example.TicTacToe.Authentication.AuthenticationRequest;
import com.example.TicTacToe.Authentication.AuthenticationResponse;
import com.example.TicTacToe.Entities.ActiveUserStore;
import com.example.TicTacToe.Entities.User;
import com.example.TicTacToe.Handler.CustomExceptions.MaxUserException;
import com.example.TicTacToe.Handler.CustomExceptions.UserAlreadyLoggedInException;
import com.example.TicTacToe.Repositories.RoleRepository;
import com.example.TicTacToe.Repositories.UserRepository;
import com.example.TicTacToe.Security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ActiveUserStore activeUsers;


    public void register(AuthenticationRequest request) {
        var userRole = roleRepository.findByName("USER")
        //todo better exception handling
                .orElseThrow(() -> new IllegalStateException("Role USER was not initialized"));

        if(userRepository.findByUsername(request.getUsername()).isEmpty()) {
            var user = User.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .accountLocked(false)
                    .enabled(true)
                    .role(userRole)
                    .build();

            userRepository.save(user);
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        if (activeUsers.getNumberOfActiveUsers() == 2)
            throw new MaxUserException("This game is full");
        if(activeUsers.findUserByUsername(request.getUsername()))
            throw new UserAlreadyLoggedInException("This user is already active");


        var newAuthentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        Map<String, Object> claims = new HashMap<String, Object>();
        User user = (User) newAuthentication.getPrincipal();
        claims.put("username", user.getUsername());

        jwtService.revokeAllValidTokensByUser(user);
        String jwtToken = jwtService.generateToken(claims, user);
        jwtService.saveToken(user, jwtToken);

        String player = correctPlayerTurn();
        activeUsers.addUser(user.getUsername(), jwtToken, player);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .player(player)
                .build();
    }

    private String correctPlayerTurn() {

        if(activeUsers.getNumberOfActiveUsers() == 0)
            return "playerX";

        if (activeUsers.getAtIndex(0).getPlayerTurn().equals("playerO"))
            return "playerX";
        else
            return "playerO";
    }

    public Integer getNumberOfActiveUsers(){return activeUsers.getNumberOfActiveUsers();}
}

