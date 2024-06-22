package com.example.TicTacToe.Entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ActiveUserInfo {
    private String username;
    private String token;
    private String playerTurn;
}
