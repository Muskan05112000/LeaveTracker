package com.leavetracker.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "employees")
public class Employee {
    @Id
    private String id;
    private String name;
    private String location;
    private String team;
    private Long associateId;
    private String role; // Employee, Manager, Lead
}
