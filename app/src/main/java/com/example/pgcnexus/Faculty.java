package com.example.pgcnexus;

public class Faculty {
    private String id;
    private String name;
    private String description;
    private String createdAt;

    // Default constructor required for Firebase
    public Faculty() {
    }

    public Faculty(String name, String description) {
        this.name = name;
        this.description = description;
        this.createdAt = new java.util.Date().toString();
    }

    public Faculty(String name) {
        this.name = name;
        this.description = "";
        this.createdAt = new java.util.Date().toString();
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
} 