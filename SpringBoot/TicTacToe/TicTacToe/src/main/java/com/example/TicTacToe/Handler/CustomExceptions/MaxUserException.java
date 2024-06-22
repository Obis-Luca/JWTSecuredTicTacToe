package com.example.TicTacToe.Handler.CustomExceptions;

public class MaxUserException extends RuntimeException {

    public MaxUserException(String message) {
        super(message);
    }
}
