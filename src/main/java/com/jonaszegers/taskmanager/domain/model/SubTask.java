package com.jonaszegers.taskmanager.domain.model;


import java.util.Objects;

public class SubTask {
    private int id;
    private String title;
    private String description;

    public SubTask(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public SubTask(String title, String description) {
        this.id = -1;
        this.title = title;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubTask task = (SubTask) o;
        return getTitle().equals(task.getTitle()) &&
                getDescription().equals(task.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getDescription());
    }

    @Override
    public String toString() {
        return "-> " + title;
    }

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
