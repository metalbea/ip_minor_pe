package com.jonaszegers.taskmanager.domain.model;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

public class TaskTest {
    LocalDateTime nu;
    Task t1;
    Task t2;
    SubTask s1;
    SubTask s2;
    SubTask s3;
    SubTask s4;

    @Before
    public void setup()
    {
        nu = LocalDateTime.now();
        t1 = new Task(1,"title 1", "descrp 1", nu);
        t2 = new Task(2,"title 2", "descrp 2", nu);
        s1 = new SubTask(1,"subtitle 1","subdescrp 1");
        s2 = new SubTask(1,"subtitle 2","subdescrp 2");
        s3 = new SubTask(1,"subtitle 3","subdescrp 3");
        s4 = new SubTask(1,"subtitle 4","subdescrp 4");
        t1.addSubtask(s1);
        t2.addSubtask(s2);
        t2.addSubtask(s3);
        t1.addSubtask(s4);
    }

    @Test
    public void edit_subtask_of_task_success()
    {
        t1.editSubtask(new SubTask(1,"new subtitle 1", "subdescrp 1"));
        Assert.assertEquals("new subtitle 1",t1.getSubTask(1).getTitle());
    }

    @Test
    public void remove_existing_subtask_from_task()
    {
        t2.removeSubTask(2);
        Assert.assertNull(t2.getSubTask(2));
    }
}
