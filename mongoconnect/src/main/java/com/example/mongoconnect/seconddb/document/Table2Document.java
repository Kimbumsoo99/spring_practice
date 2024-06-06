package com.example.mongoconnect.seconddb.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "table2")
@Data
public class Table2Document {
    @Id
    String _id;
    String data;
}

