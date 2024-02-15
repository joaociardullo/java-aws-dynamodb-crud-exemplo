package br.com.joaozinho.dynamodbcrud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;

@Configuration
public class DynamobConfig {
    public static final String HTTP_LOCALHOST_4566 = "http://localhost:4566";

    //configurando o banco de dados nao relacional Dynamodb

    @Bean
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
                .endpointOverride(URI.create(HTTP_LOCALHOST_4566))
                .region(Region.SA_EAST_1)
                .build();
    }

}

