package com.example.TicTacToe.Entities;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Component
public class Game {

    private String board;
    private String status;
    private Boolean isXTurn;

    public Game() {
        board="---------";
        status = "ongoing";
        isXTurn = true;

    }

    @Override
    public String toString()
    {
        return board + "," + status + "," + isXTurn;
    }


    public void switchTurn()
    {
        isXTurn = !isXTurn;
    }
}
