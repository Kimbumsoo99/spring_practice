package com.example.mongoconnect.repository;

import com.example.mongoconnect.document.Table1Document;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Table1Repository extends MongoRepository<Table1Document, String> {
}
