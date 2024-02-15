package br.com.joaozinho.dynamodbcrud.entity;

import br.com.joaozinho.dynamodbcrud.records.ScoreDTO;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.time.Instant;
import java.util.UUID;

@DynamoDbBean
public class PlayerHistory {

    private String playerId;
    private UUID gameId;
    private Double score;
    private Instant createdAt;

    //Construtor
    public static PlayerHistory fromScore(String playerId, ScoreDTO scoreDTO) {
        var playHistory = new PlayerHistory();

        playHistory.setPlayerId(playerId);
        playHistory.setGameId(UUID.randomUUID());
        playHistory.setCreatedAt(Instant.now());
        playHistory.setScore(scoreDTO.score());

        return playHistory;
    }


    @DynamoDbPartitionKey
    @DynamoDbAttribute("player_id")
    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("game_id")
    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    @DynamoDbAttribute("score")
    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    @DynamoDbAttribute("created_at")
    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
