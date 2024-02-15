package br.com.joaozinho.dynamodbcrud.service;

import br.com.joaozinho.dynamodbcrud.entity.PlayerHistory;
import br.com.joaozinho.dynamodbcrud.records.ScoreDTO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface PlayerServiceImpl {
    PlayerHistory create(@PathVariable("playerId") String playerId, @RequestBody ScoreDTO scoreDto);
}
