package com.example.mongoconnect.firstdb.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "table1")
@Data
public class Table1Document {
    @Id
    String _id;
    String data;
}
