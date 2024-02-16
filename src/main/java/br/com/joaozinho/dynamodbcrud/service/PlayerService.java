package br.com.joaozinho.dynamodbcrud.service;

import br.com.joaozinho.dynamodbcrud.entity.PlayerHistory;
import br.com.joaozinho.dynamodbcrud.records.ScoreDTO;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.util.List;

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

    @GetMapping("/{playerId}/games")
    public ResponseEntity<List<PlayerHistory>> listGames(@PathVariable("playerId") String playerId) {

        var key = Key.builder().partitionValue(playerId).build();

        var conditional = QueryConditional.keyEqualTo(key);

        var playerHistoryList = dynamoDbTemplate.query(QueryEnhancedRequest.builder()
                        .queryConditional(conditional).build(),
                PlayerHistory.class);

        return ResponseEntity.ok(playerHistoryList.items().stream().toList());

    }

    @GetMapping("/{playerId}/games/{gameId}")
    public ResponseEntity<PlayerHistory> getById(@PathVariable("playerId") String playerId,
                                                 @PathVariable("gameId") String gameId) {
        var user = dynamoDbTemplate.load(Key.builder()
                .partitionValue(playerId)
                .sortValue(gameId)
                .build(), PlayerHistory.class);

        return user == null ?
                ResponseEntity.notFound().build() : ResponseEntity.ok(user);
    }

    @DeleteMapping("/{playerId}/games/{gameId}")
    public ResponseEntity<Void> delete(@PathVariable("playerId") String playerId,
                                       @PathVariable("gameId") String gameId) {
        var key = Key.builder()
                .partitionValue(playerId)
                .sortValue(gameId)
                .build();

        var player = dynamoDbTemplate.load(key, PlayerHistory.class);

        if (player == null) {
            return ResponseEntity.notFound().build();
        }

        dynamoDbTemplate.delete(key, PlayerHistory.class);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{playerId}/games/{gameId}")
    public ResponseEntity<Void> update(@PathVariable("playerId") String playerId,
                                       @PathVariable("gameId") String gameId,
                                       @RequestBody ScoreDTO scoreDto) {
        var key = Key.builder()
                .partitionValue(playerId)
                .sortValue(gameId)
                .build();

        var player = dynamoDbTemplate.load(key, PlayerHistory.class);

        if (player == null) {
            return ResponseEntity.notFound().build();
        }

        player.setScore(scoreDto.score());

        dynamoDbTemplate.save(player);

        return ResponseEntity.noContent().build();
    }
}
