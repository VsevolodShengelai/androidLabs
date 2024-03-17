package com.example.lab4;

import android.app.Application;

import com.example.lab4.models.TaskModel;

import java.util.ArrayList;

public class MyApp extends Application {
    private ArrayList<TaskModel> notes = new ArrayList<>();

    public ArrayList<TaskModel> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<TaskModel> notes) {
        this.notes = notes;
    }
}