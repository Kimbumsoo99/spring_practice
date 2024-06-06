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
        basePackages = "com.example.mongoconnect.firstdb.repository",
        mongoTemplateRef = "firstMongoTemplate"
)
public class FirstDBConfig {

    @Primary
    @Bean
    public MongoClient firstMongoClient(){
        return MongoClients.create("mongodb://localhost:27017");
    }

    @Primary
    @Bean
    public MongoDatabaseFactory firstDatabaseFactory(){
        return new SimpleMongoClientDatabaseFactory(firstMongoClient(), "testdb1");
    }

    @Primary
    @Bean
    public MongoTemplate firstMongoTemplate(){
        return new MongoTemplate(firstDatabaseFactory());
    }
}
