package com.leavetracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private Integer associateId;
    private String password; // hashed
    private String role; // Employee, Manager, Lead
}
