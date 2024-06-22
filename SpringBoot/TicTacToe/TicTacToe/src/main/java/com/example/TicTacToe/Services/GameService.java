package com.example.TicTacToe.Services;

import com.example.TicTacToe.Entities.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameService {

    private Game game;

    public Game getGame() {
        if (game == null) {
            resetGame();
        }
        return game;
    }

    public String makeMove(int pos, char player) {
        // Validate game state
        if(!game.getStatus().equals("ongoing"))
            throw new IllegalArgumentException("Game is already over");

        // Validate character
        if (player != 'X' && player != 'O') {
            throw new IllegalArgumentException("Move must be 'X' or 'O'");
        }

        // Validate turn
        if ((game.getIsXTurn() && player != 'X') || (!game.getIsXTurn() && player != 'O')) {
            throw new IllegalArgumentException("Not your turn!");
        }

        // Validate position
        if (pos < 0 || pos >= game.getBoard().length()) {
            throw new IllegalArgumentException("Position must be between 0 and 8");
        }

        // Ensure the position is not already taken
        if (game.getBoard().charAt(pos) != '-') {
            throw new IllegalArgumentException("Position already taken");
        }

        // Update the game.getBoard()
        StringBuilder newGame = new StringBuilder(game.getBoard());
        newGame.setCharAt(pos, player);
        game.setBoard(newGame.toString());

        // Check if the game is won
        if (checkWin(player))
            game.setStatus(player+ " wins");
        else if (!game.getBoard().contains("-"))
            // Check if the game is a draw
            game.setStatus("draw");
        else
        {
            // Continue the game
            game.switchTurn();
            game.setStatus("ongoing");
        }


        return game.getStatus();
    }

    private boolean checkWin(char player) {
        // Winning combinations
        int[][] winPositions = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
                {0, 4, 8}, {2, 4, 6}              // Diagonals
        };

        // Check if any winning combination is achieved
        for (int[] positions : winPositions) {
            if (game.getBoard().charAt(positions[0]) == player &&
                    game.getBoard().charAt(positions[1]) == player &&
                    game.getBoard().charAt(positions[2]) == player) {
                return true;
            }
        }
        return false;
    }

    public void resetGame()
    {
        this.game = new Game();
    }

}
