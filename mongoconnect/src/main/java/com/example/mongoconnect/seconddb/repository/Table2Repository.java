package com.example.mongoconnect.seconddb.repository;

import com.example.mongoconnect.seconddb.document.Table2Document;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Table2Repository extends MongoRepository<Table2Document, String> {
}
