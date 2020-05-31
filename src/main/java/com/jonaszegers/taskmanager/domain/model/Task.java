package com.jonaszegers.taskmanager.domain.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Task {
    private int id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private List<SubTask> subTasks;

    public Task(int id, String title, String description, LocalDateTime dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        subTasks = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return getTitle().equals(task.getTitle()) &&
                getDescription().equals(task.getDescription()) &&
                getDueDate().equals(task.getDueDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getDescription(), getDueDate());
    }

    @Override
    public String toString() {
        return title + ": due to " + dueDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy 'at' hh:mm a"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<SubTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<SubTask> subTasks) {
        this.subTasks = subTasks;
    }

    public SubTask getSubTask(int id)
    {
        for (SubTask dum:subTasks) {
            if(dum.getId()==id)
            {
                return dum;
            }
        }
        return null;
    }

    public void addSubtask(SubTask subTask) {
        if(subTasks.size()!=0)
        {
            subTask.setId(subTasks.get(subTasks.size() - 1).getId() + 1);
        }
        else
        {
            subTask.setId(1);
        }
        subTasks.add(subTask);
    }

    public void editSubtask(SubTask subTask) {
        for (int i = 0; i < subTasks.size(); i++) {
            if (subTasks.get(i).getId() == id) {
                subTasks.get(i).setDescription(subTask.getDescription());
                subTasks.get(i).setTitle(subTask.getTitle());
                return;
            }
        }

    }

    public void removeSubTask(int id) {
        for (int i = 0; i < subTasks.size(); i++) {
            if (subTasks.get(i).getId() == id) {
                subTasks.remove(i);
                while (i < subTasks.size()) {
                    subTasks.get(i).setId(subTasks.get(i).getId() - 1);
                    i++;
                }
            }
        }
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

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
}
