package com.jonaszegers.taskmanager.dto;

import javax.validation.constraints.NotNull;

public class SubTaskDTO {
    private int id;
    @NotNull
    private String title;

    @NotNull
    private String description;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
