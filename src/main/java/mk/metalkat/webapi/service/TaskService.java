package mk.metalkat.webapi.service;

import mk.metalkat.webapi.models.Task;
import mk.metalkat.webapi.models.dto.TaskDTO;

import java.util.List;

public interface TaskService {

    Task getTask(Long taskId);

    Task createTask(TaskDTO task);

    Task updateTask(Long taskId, Task task);

    Task deleteTask(Long taskId);

    List<Task> getAllTask();

    Task setMachine(Long taskId, Long machineId);

    Task setEmployee(Long taskId, Long employeeId);

    Task setCncCode(Long taskId, Long cncId);
}
