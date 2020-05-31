package com.jonaszegers.taskmanager.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaskDTO {
    /*private int id;*/
    @NotEmpty
    private String title;

    @NotEmpty
    private String description;


    @FutureOrPresent
    private LocalDateTime dueDate;

    /*public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }*/

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

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDateStr) {
        dueDateStr = dueDateStr.replace('T',' ');
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dueDate = LocalDateTime.parse(dueDateStr, formatter);

        this.dueDate = dueDate;
    }
}
