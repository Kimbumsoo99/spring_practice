package com.example.mongoconnect.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(
        basePackages = "com.example.mongoconnect.seconddb.repository",
        mongoTemplateRef = "secondMongoTemplate"
)
public class SecondDBConfig {

    @Bean
    public MongoClient secondMongoClient(){
        return MongoClients.create("mongodb://localhost:27017");
    }

    @Bean
    public MongoDatabaseFactory secondDatabaseFactory(){
        return new SimpleMongoClientDatabaseFactory(secondMongoClient(), "test2");
    }

    @Bean
    public MongoTemplate secondMongoTemplate(){
        return new MongoTemplate(secondDatabaseFactory());
    }
}
