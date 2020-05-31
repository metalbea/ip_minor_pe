package com.jonaszegers.taskmanager.domain.service;

import com.jonaszegers.taskmanager.domain.model.SubTask;
import com.jonaszegers.taskmanager.domain.model.Task;
import com.jonaszegers.taskmanager.dto.SubTaskDTO;
import com.jonaszegers.taskmanager.dto.TaskDTO;

import java.util.List;

public interface TaskService {

    void addTask(TaskDTO t);

    void editTask(int id, TaskDTO t);

    void removeTask(int id);

    void addSubTask(int id, SubTaskDTO subTask);

    void editSubTask(int id, int subId, SubTaskDTO subTask);

    void removeSubTask(int id, int subId);

    Task getTask(int id);

    List<SubTask> getSubtasks(int taskId);
/*
    Task getTask(String title, String description, LocalDateTime dueDate);*/

    List<Task> getTasks();

    SubTask getSubTask(int subId);
}
