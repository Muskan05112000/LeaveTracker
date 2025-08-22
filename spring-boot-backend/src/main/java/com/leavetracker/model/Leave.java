package com.leavetracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "leaves")
public class Leave {
    @Id
    private String id;
    private String date; // ISO format
    private String employee; // employee name
    private String type; // Planned, Emergency, Sick
}
