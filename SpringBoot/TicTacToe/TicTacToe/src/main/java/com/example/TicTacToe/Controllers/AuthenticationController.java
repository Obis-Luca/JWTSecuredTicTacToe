package com.example.TicTacToe.Controllers;

import com.example.TicTacToe.Authentication.AuthenticationRequest;
import com.example.TicTacToe.Authentication.AuthenticationResponse;
import com.example.TicTacToe.Services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth")
@RequiredArgsConstructor
//@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService service;


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> Register(
            @RequestBody @Valid AuthenticationRequest request){

        service.register(request);
        return ResponseEntity.accepted().build();

    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest request)
    {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/active-users")
    public ResponseEntity<Integer> getActiveUsers()
    {
        return ResponseEntity.ok(service.getNumberOfActiveUsers());
    }

}
