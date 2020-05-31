package com.jonaszegers.taskmanager.repository;

import com.jonaszegers.taskmanager.domain.model.SubTask;
import com.jonaszegers.taskmanager.domain.model.Task;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/*@Repository*/
public class TaskRepositoryLocal implements TaskRepository{
    List<Task> tasks;

    public TaskRepositoryLocal() {
        tasks = new ArrayList<>();
        tasks.add(new Task(1, "Task 1", "description 1", LocalDateTime.now()));
        tasks.add(new Task(2, "Task 2", "description 2", LocalDateTime.now()));
        tasks.get(1).addSubtask(new SubTask("Subtask 2.1","lmao"));
        tasks.get(1).addSubtask(new SubTask("Subtask 2.2","rofl"));
        tasks.get(1).addSubtask(new SubTask("Subtask 2.3","lol"));
        tasks.add(new Task(3, "Task 3", "description 3", LocalDateTime.now()));
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Task getTask(int id) {
        for (Task t : tasks) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

    public void editTask(Task t) {
        for (Task dumt : tasks) {
            if (dumt.getId() == t.getId()) {
                dumt.setDescription(t.getDescription());
                dumt.setDueDate(t.getDueDate());
                dumt.setTitle(t.getTitle());
                return;
            }
        }
    }

    @Override
    public List<SubTask> getSubtasks(int taskId) {
        return null;
    }

    @Override
    public SubTask getSubTask(int subId) {
        return null;
    }
/*public Task getTask(Task t)
    {
        for (Task dumt: tasks)
        {
            if(dumt.equals(t))
            {
                return  dumt;
            }
        }
        return null;
    }*/

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void addSubTask(int id, SubTask subTask) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                tasks.get(i).addSubtask(subTask);
                return;
            }
        }
    }

    public void editSubTask(int id, SubTask subTask)
    {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                tasks.get(i).editSubtask(subTask);
                return;
            }
        }
    }

    public void removeTask(int id) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                tasks.remove(i);
                while (i < tasks.size()) {
                    tasks.get(i).setId(tasks.get(i).getId() - 1);
                    i++;
                }
                return;
            }
        }
    }

    public void removeSubTask(int id, int subId) {
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId() == id) {
                tasks.get(i).removeSubTask(subId);
                return;
            }
        }
    }
}
