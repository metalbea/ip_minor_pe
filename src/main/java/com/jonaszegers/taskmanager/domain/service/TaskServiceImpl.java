package com.jonaszegers.taskmanager.domain.service;

import com.jonaszegers.taskmanager.domain.model.SubTask;
import com.jonaszegers.taskmanager.domain.model.Task;
import com.jonaszegers.taskmanager.dto.SubTaskDTO;
import com.jonaszegers.taskmanager.dto.TaskDTO;
import com.jonaszegers.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository repository;

    @Autowired
    public TaskServiceImpl(TaskRepository repository) {
        this.repository = repository;
    }

    public void addTask(TaskDTO t) {
        /*repository.addTask(new Task(t.getId(),t.getTitle(),t.getDescription(),t.getDueDate()));*/
        repository.addTask(new Task(repository.getTasks().get(repository.getTasks().size() - 1).getId() + 1, t.getTitle(), t.getDescription(), t.getDueDate()));
    }

    @Override
    public void editTask(int id, TaskDTO t) {
        repository.editTask(new Task(id, t.getTitle(), t.getDescription(), t.getDueDate()));
    }

    @Override
    public List<SubTask> getSubtasks(int taskId) {
        return repository.getSubtasks(taskId);
    }

    @Override
    public SubTask getSubTask(int subId) {
        return repository.getSubTask(subId);
    }

    @Override
    public void addSubTask(int id, SubTaskDTO subTaskDTO) {
        repository.addSubTask(id, new SubTask(subTaskDTO.getTitle(), subTaskDTO.getDescription()));
    }

    @Override
    public void editSubTask(int id, int subId, SubTaskDTO subTaskDTO) {
        repository.editSubTask(id, new SubTask(subId, subTaskDTO.getTitle(), subTaskDTO.getDescription()));
    }

    @Override
    public void removeSubTask(int id, int subId) {
        repository.removeSubTask(id, subId);
    }

    public void removeTask(int id) {
        repository.removeTask(id);
        /*repository.removeTask(new Task(t.getTitle(),t.getDescription(),t.getDueDate()));*/
    }

    public Task getTask(int id) {
        return repository.getTask(id);
    }

    /*@Override
    public Task getTask(String title, String description, LocalDateTime dueDate) {
        return repository.getTask(new Task(title,description,dueDate));
    }*/

    public List<Task> getTasks() {
        return repository.getTasks();
    }

}
