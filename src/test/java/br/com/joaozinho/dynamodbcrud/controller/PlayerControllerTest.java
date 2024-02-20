package br.com.joaozinho.dynamodbcrud.controller;

import br.com.joaozinho.dynamodbcrud.entity.PlayerHistory;
import br.com.joaozinho.dynamodbcrud.records.ScoreDTO;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.PageIterable;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerControllerTest {

    @Mock
    private DynamoDbTemplate dynamoDbTemplate;

    @InjectMocks
    private PlayerController playerController;

    @Captor
    private ArgumentCaptor<Key> keyCaptor;

    @BeforeEach
    void setUp() {
    }

    @Test
    void create() {
        // Given
        String playerId = "player1";
        ScoreDTO scoreDto = new ScoreDTO(100.0);

        // When
        playerController.create(playerId, scoreDto);

        // Then
        verify(dynamoDbTemplate).save(any(PlayerHistory.class));
    }

    @Test
    void getById() {
        // Given
        String playerId = "player1";
        String gameId = "game1";
        PlayerHistory playerHistory = new PlayerHistory();
        when(dynamoDbTemplate.load(any(Key.class), eq(PlayerHistory.class))).thenReturn(playerHistory);

        // When
        ResponseEntity<PlayerHistory> response = playerController.getById(playerId, gameId);

        // Then
        verify(dynamoDbTemplate).load(keyCaptor.capture(), eq(PlayerHistory.class));
        assertNotNull(response.getBody());
        assertEquals(playerHistory, response.getBody());
    }

    @Test
    void delete() {
        // Given
        String playerId = "player1";
        String gameId = "game2";
        when(dynamoDbTemplate.load(any(Key.class), eq(PlayerHistory.class))).thenReturn(new PlayerHistory());

        // When
        ResponseEntity<Void> response = playerController.delete(playerId, gameId);

        // Then
        verify(dynamoDbTemplate).delete(keyCaptor.capture(), eq(PlayerHistory.class));
        assertEquals(ResponseEntity.noContent().build(), response);
    }

    @Test
    void update() {
        // Given
        String playerId = "player1";
        String gameId = "game3";
        ScoreDTO scoreDto = new ScoreDTO(200.0);
        PlayerHistory player = new PlayerHistory();
        when(dynamoDbTemplate.load(any(Key.class), eq(PlayerHistory.class))).thenReturn(player);

        // When
        ResponseEntity<Void> response = playerController.update(playerId, gameId, scoreDto);

        // Then
        verify(dynamoDbTemplate).save(any(PlayerHistory.class));
        assertEquals(ResponseEntity.noContent().build(), response);
    }
}
