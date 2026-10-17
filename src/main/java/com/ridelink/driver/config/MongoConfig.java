package com.ridelink.driver.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    private static final String CONNECTION_URI =
            "mongodb+srv://ridelink:1234@cluster0.a7sm8rg.mongodb.net/ridelink-accounts?retryWrites=true&w=majority";

    private static final String DATABASE_NAME = "ridelink-accounts";

    @Bean
    @Primary
    @ConditionalOnMissingBean(MongoClient.class)
    public MongoClient mongoClient() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(CONNECTION_URI))
                .build();
        return MongoClients.create(settings);
    }

    @Bean
    @Primary
    @ConditionalOnMissingBean(MongoTemplate.class)
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, DATABASE_NAME);
    }
}
