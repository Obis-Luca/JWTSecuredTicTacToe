package com.example.TicTacToe.Services;

import com.example.TicTacToe.Entities.ActiveUserStore;
import com.example.TicTacToe.Entities.Token;
import com.example.TicTacToe.Repositories.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final GameService gameService;
    private final TokenRepository tokenRepository;
    private final ActiveUserStore activeUsers;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        final String authHeader = request.getHeader(AUTHORIZATION);

        if(authHeader == null || !authHeader.startsWith("Bearer "))
        {
            return;
        }

        final String jwt;
        jwt = authHeader.substring(7);

        Token storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);

        if(storedToken != null)
        {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            activeUsers.removeUser(jwt);
            gameService.resetGame();
            SecurityContextHolder.clearContext();
        }

    }
}
