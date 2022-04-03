package com.bussolini.tareas.controller.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ToDoAPI implements Serializable {

    private Integer userId;

    private Integer id;

    private String title;

    private boolean completed;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String isCompleted() {
        String status = "PENDIENTE";
        if (completed)
            status = "REALIZADA";
        return status;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
