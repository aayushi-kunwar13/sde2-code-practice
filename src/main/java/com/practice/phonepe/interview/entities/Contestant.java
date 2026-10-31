package com.practice.phonepe.interview.entities;

import java.util.UUID;

public class Contestant {

    private final String id;
    private final String name;
    private final String department;

    //add a field to store createdat
    public Contestant(String name, String department) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.department = department;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }
}
