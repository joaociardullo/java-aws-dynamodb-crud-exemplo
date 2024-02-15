package br.com.joaozinho.dynamodbcrud.controller;

import br.com.joaozinho.dynamodbcrud.entity.PlayerHistory;
import br.com.joaozinho.dynamodbcrud.records.ScoreDTO;
import br.com.joaozinho.dynamodbcrud.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/players")
public class PlayerController {
    private final PlayerService service;

    public PlayerController(PlayerService service) {
        this.service = service;
    }


    @PostMapping("/{playerId}/games")
    public ResponseEntity<PlayerHistory> criandoPlayer(@PathVariable("playerId") String playerId,
                                                       @RequestBody ScoreDTO scoreDto) {
        PlayerHistory playerHistory = service.create(playerId, scoreDto);

        return new ResponseEntity<>(playerHistory, HttpStatus.CREATED);
    }
}
