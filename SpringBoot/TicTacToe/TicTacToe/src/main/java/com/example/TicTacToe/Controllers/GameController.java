package com.example.TicTacToe.Controllers;

import com.example.TicTacToe.Entities.Game;
import com.example.TicTacToe.Entities.MoveRequest;
import com.example.TicTacToe.Services.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping
    public ResponseEntity<?> getGame()
    {
        return ResponseEntity.ok().body(gameService.getGame().toString());
    }

    @PostMapping("/reset")
    public ResponseEntity<?> resetGame()
    {
        try {
            gameService.resetGame();
            return ResponseEntity.ok().body(gameService.getGame().toString());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping
    public ResponseEntity<?> makeMove(@RequestBody MoveRequest move)
    {
        Game game = gameService.getGame();
        try {
            int pos = Integer.parseInt(move.getMovePosition());
            char player = move.getPlayer().charAt(0);
            String status = gameService.makeMove(pos, player);

            return ResponseEntity.ok().body(game.getBoard() + ";" + game.getStatus() + ";" + game.getIsXTurn());

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
