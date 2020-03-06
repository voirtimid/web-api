package mk.metalkat.webapi.controller;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.models.jpa.Task;
import mk.metalkat.webapi.models.dto.DateTimeDTO;
import mk.metalkat.webapi.models.dto.TaskDTO;
import mk.metalkat.webapi.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/tasks")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.43.211:3000"})
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public List<Task> getAllTask() {
        return taskService.getAllTasks();
    }

    @GetMapping("/machine/{machineId}")
    public List<Task> getAllTasksForMachine(@PathVariable("machineId") Long machineId) {
        return taskService.getAllTasksForMachine(machineId);
    }

    @GetMapping("/employee/{employeeId}")
    public List<Task> getAllTasksForEmployee(@PathVariable("employeeId") Long employeeId) {
        return taskService.getAllTasksForEmployee(employeeId);
    }

    @PostMapping
    public Task createTask(@RequestBody TaskDTO task) {
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

    @PutMapping
    public Task updateTask(@RequestBody TaskDTO taskDTO) {
        return taskService.updateTask_v2(taskDTO);
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

//    @PutMapping(value = "/{taskId}/startWorkTime")
//    public Task startWorkTime(@PathVariable("taskId") Long taskId) {
//        return taskService.startTaskWorkTime(taskId);
//    }
//
//    @PutMapping(value = "/{taskId}/endWorkTime")
//    public Task endWorkTime(@PathVariable("taskId") Long taskId) {
//        return taskService.endTaskWorkTime(taskId);
//    }
//
    @PutMapping(value = "/{taskId}/complete")
    public Task completeTask(@PathVariable("taskId") Long taskId) {
        return taskService.completeTask(taskId);
    }

    @PostMapping(value = "/checkTimeSlots")
    public boolean isTimeSlotAvailable(@RequestBody DateTimeDTO dateTimeDTO) {
        return taskService.checkIfSlotIsAvailable(dateTimeDTO);
    }

    @GetMapping(value = "/findSlot/{machineId}")
    public LocalDate findFirstAvailableSlot(@PathVariable("machineId") Long machineId) {
        return taskService.findFirstAvailableSlot(machineId);
    }
}
