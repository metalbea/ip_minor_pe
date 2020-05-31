package com.jonaszegers.taskmanager.repository;

import com.jonaszegers.taskmanager.domain.model.SubTask;
import com.jonaszegers.taskmanager.domain.model.Task;

import java.util.List;

public interface TaskRepository {

    public List<Task> getTasks();

    public Task getTask(int id);

    public void editTask(Task t);

    /*public Task getTask(Task t);*/

    public void addTask(Task task);

    public void addSubTask(int id, SubTask subTask);

    public void editSubTask(int id, SubTask subTask);

    public void removeTask(int id);

    public void removeSubTask(int id, int subId);

    List<SubTask> getSubtasks(int taskId);

    SubTask getSubTask(int subId);
}
