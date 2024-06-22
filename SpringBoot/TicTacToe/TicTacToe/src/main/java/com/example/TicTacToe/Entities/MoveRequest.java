package com.example.TicTacToe.Entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MoveRequest {
    String movePosition;
    String player;
}
