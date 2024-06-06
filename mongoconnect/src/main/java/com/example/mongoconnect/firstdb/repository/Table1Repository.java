package com.example.mongoconnect.firstdb.repository;

import com.example.mongoconnect.firstdb.document.Table1Document;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Table1Repository extends MongoRepository<Table1Document, String> {
}
