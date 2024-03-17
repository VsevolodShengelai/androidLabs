package com.example.lab3.models;

public class TaskModel {
    private int id;
    private String name;
    private String description;

    private static int lastId = -1;

    public TaskModel (String name, String description){
        lastId +=1;
        this.id = lastId;
        this.name = name;
        this.description = description;
    }

    public static int getLastId() {
        return lastId;
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
}
