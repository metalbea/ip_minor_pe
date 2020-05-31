package com.jonaszegers.taskmanager.ui.controller;

import com.jonaszegers.taskmanager.domain.service.TaskService;
import com.jonaszegers.taskmanager.dto.SubTaskDTO;
import com.jonaszegers.taskmanager.dto.TaskDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
/*@RequestMapping("/tasks")*/
public class TaskController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public String getTasks(Model model) {
        model.addAttribute("tasks", taskService.getTasks());
        return "overview";
    }

    @GetMapping("/tasks/{id}")
    public String getTask(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("curtask", taskService.getTask(id));
        model.addAttribute("subTasks",taskService.getSubtasks(id));
        return "task";
    }

    @GetMapping("/tasks/edit/{id}")
    public String goToEditTask(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("curtask", taskService.getTask(id));
        return "edit";
    }

    @PostMapping("/tasks/edit/{id}")
    public String editTask(@Valid TaskDTO taskDTO, @PathVariable("id") Integer id, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            taskService.editTask(id, taskDTO);
            return "redirect:/tasks/{id}";
        } else {
            System.out.println(bindingResult.getAllErrors());
            return "/tasks/edit/{id}";
        }
    }

    @GetMapping("/tasks/{id}/sub/{subId}/edit")
    public String goToEditSubTask(Model model, @PathVariable("id") Integer id, @PathVariable("subId") Integer subId) {
        model.addAttribute("curtask", taskService.getSubTask(subId));
        model.addAttribute("taskId",id);
        model.addAttribute("subId", subId);
        return "editSub";
    }

    @PostMapping("/tasks/{id}/sub/{subId}/edit")
    public String editSubTask(@Valid SubTaskDTO subTaskDTO, @PathVariable("id") Integer id, @PathVariable("subId") Integer subId, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {

            taskService.editSubTask(id, subId, subTaskDTO);
            return "redirect:/tasks/{id}";
        } else {
            System.out.println(bindingResult.getAllErrors());
            return "/tasks/edit/{id}";
        }
    }

    @GetMapping("/tasks/new")
    public String goToCreate(Model model) {
        return "create";
    }

    @GetMapping("/tasks/{id}/sub/{subId}/remove")
    public String removeSubTask(@PathVariable("id") Integer id, @PathVariable("subId") Integer subId) {
        taskService.removeSubTask(id, subId);
        return "redirect:/tasks/{id}";
    }

    @GetMapping("/tasks/{id}/remove")
    public String removeTask(@PathVariable("id") Integer id) {
        taskService.removeTask(id);
        return "redirect:/tasks";
    }

    @PostMapping("/tasks/{id}/sub/create")
    public String createSub(@Valid SubTaskDTO subTaskDTO, Model model, @PathVariable("id") Integer id, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            taskService.addSubTask(id, subTaskDTO);
        } else {
            System.out.println(bindingResult.getAllErrors());
        }
        return "redirect:/tasks/{id}";
    }

    @PostMapping("/tasks/createTask")
    public String newTask(@Valid TaskDTO taskDTO, BindingResult bindingResult, @ModelAttribute("errormessages") ArrayList<String> errorsmessages) {
        if (!bindingResult.hasErrors()) {
            taskService.addTask(taskDTO);
            return "redirect:/tasks/new";
        } else {
            errorsmessages = new ArrayList<>();
            for(ObjectError error: bindingResult.getAllErrors()){

                if(!error.getDefaultMessage().contains("Property 'dueDate' threw exception;"))
                {
                    errorsmessages.add(((FieldError) error).getField() + " " + error.getDefaultMessage());
                }
                else
                {
                    errorsmessages.add("dueDate mag niet leeg zijn");
                }

            }

            return goToCreate(null);
        }
    }
}
