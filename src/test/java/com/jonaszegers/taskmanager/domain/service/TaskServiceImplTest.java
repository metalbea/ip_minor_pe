package com.jonaszegers.taskmanager.domain.service;

import com.jonaszegers.taskmanager.domain.model.Task;
import com.jonaszegers.taskmanager.dto.TaskDTO;
import com.jonaszegers.taskmanager.repository.TaskRepositorySql;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TaskServiceImplTest {
    TaskRepositorySql repo = new TaskRepositorySql();
    TaskServiceImpl taskService = new TaskServiceImpl(repo);

    @Test
    public void performTests() {
        int taskId = add_task_with_succes();
        if (taskId == 0 || taskId == -1) {
            Assert.assertTrue(false);
        }
        if (!edit_task_with_succes(taskId)) {
            Assert.assertTrue(false);
        }
        Assert.assertTrue(delete_task_with_succes(taskId));
    }

    public int add_task_with_succes() {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("test task");
        taskDTO.setDescription("test description");
        taskDTO.setDueDate("2020-05-05 23:44");
        taskService.addTask(taskDTO);
        for (Task dum : taskService.getTasks()) {
            if (dum.getTitle().equals("test task") && dum.getDescription().equals("test description")) {
                Assert.assertTrue(true);
                return dum.getId();
            }
        }
        return -1;
    }


    public boolean edit_task_with_succes(int testTaskId) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setTitle("test task 2");
        taskDTO.setDescription("new description");
        taskDTO.setDueDate("2020-05-05 23:44");
        taskService.editTask(testTaskId, taskDTO);

        for (Task dum : taskService.getTasks()) {
            if (dum.getId() == testTaskId) {
                if (dum.getTitle().equals("test task 2") && dum.getDescription().equals("new description")) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }


    public boolean delete_task_with_succes(int testTaskId) {
        taskService.removeTask(testTaskId);
        return taskService.getTask(testTaskId) == null;
    }
}
