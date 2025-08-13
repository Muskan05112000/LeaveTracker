package com.leavetracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "holidays")
public class Holiday {
    @Id
    private String id;
    private String occasion;
    private String date; // ISO format
    private List<String> locations;
    private Boolean national;
}
