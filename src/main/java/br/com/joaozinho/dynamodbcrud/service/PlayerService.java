package br.com.joaozinho.dynamodbcrud.service;

import br.com.joaozinho.dynamodbcrud.entity.PlayerHistory;
import br.com.joaozinho.dynamodbcrud.records.ScoreDTO;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class PlayerService implements PlayerServiceImpl {

    private final DynamoDbTemplate dynamoDbTemplate;


    public PlayerService(DynamoDbTemplate dynamoDbTemplate) {
        this.dynamoDbTemplate = dynamoDbTemplate;
    }

    public PlayerHistory create(@PathVariable("playerId") String playerId,
                                @RequestBody ScoreDTO scoreDto) {

        PlayerHistory playerHistory = PlayerHistory.fromScore(playerId, scoreDto);
        dynamoDbTemplate.save(playerHistory);
        return playerHistory;
    }
}
