package mk.metalkat.webapi.controller;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.models.Task;
import mk.metalkat.webapi.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/tasks")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public List<Task> getAllTask() {
        return taskService.getAllTask();
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @GetMapping(value = "/{taskId}")
    public Task getTaskById(@PathVariable("taskId") Long taskId) {
        return taskService.getTask(taskId);
    }

    @PutMapping(value = "/{taskId}")
    public Task updateTask(@PathVariable("taskId") Long taskId, @RequestBody Task task) {
        return taskService.updateTask(taskId, task);
    }

    @DeleteMapping(value = "/{taskId}")
    public Task deleteTask(@PathVariable("taskId") Long taskId) {
        return taskService.deleteTask(taskId);
    }

    @PutMapping(value = "/{taskId}/addMachine/{machineId}")
    public Task addMachine(@PathVariable("taskId") Long taskId, @PathVariable("machineId") Long machineId) {
        return taskService.setMachine(taskId, machineId);
    }

    @PutMapping(value = "/{taskId}/addEmployee/{employeeId}")
    public Task addEmployee(@PathVariable("taskId") Long taskId, @PathVariable("employeeId") Long employeeId) {
        return taskService.setEmployee(taskId, employeeId);
    }

    @PutMapping(value = "/{taskId}/addCnc/{cncId}")
    public Task addCncCode(@PathVariable("taskId") Long taskId, @PathVariable("cncId") Long cncId) {
        return taskService.setCncCode(taskId, cncId);
    }
}
