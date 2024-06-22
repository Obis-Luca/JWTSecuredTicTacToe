package com.example.TicTacToe.Utils;

import com.example.TicTacToe.Entities.Game;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GameConfig {

    @Bean
    public Game game() {
        return new Game();
    }
}
