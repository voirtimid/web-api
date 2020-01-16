package mk.metalkat.webapi.service;

import mk.metalkat.webapi.models.Task;

import java.util.List;

public interface TaskService {

    Task getTask(Long taskId);

    Task createTask(Task task);

    Task updateTask(Long taskId, Task task);

    Task deleteTask(Long taskId);

    List<Task> getAllTask();

    Task setMachine(Long taskId, Long machineId);

    Task setEmployee(Long taskId, Long employeeId);

    Task setCncCode(Long taskId, Long cncId);
}
