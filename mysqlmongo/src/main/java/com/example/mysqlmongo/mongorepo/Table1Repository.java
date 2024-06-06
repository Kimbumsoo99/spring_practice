package com.example.mysqlmongo.mongorepo;

import com.example.mysqlmongo.document.Table1Document;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Table1Repository extends MongoRepository<Table1Document, String> {
}
