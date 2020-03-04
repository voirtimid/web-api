package mk.metalkat.webapi.service;

import mk.metalkat.webapi.models.jpa.Task;
import mk.metalkat.webapi.models.dto.DateTimeDTO;
import mk.metalkat.webapi.models.dto.TaskDTO;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {

    Task getTask(Long taskId);

    Task createTask(TaskDTO task);

    Task updateTask(Long taskId, Task task);

    Task updateTask_v2(TaskDTO taskDTO);

    Task deleteTask(Long taskId);

    List<Task> getAllTasks();

    Task setMachine(Long taskId, Long machineId);

    Task setEmployee(Long taskId, Long employeeId);

    Task setCncCode(Long taskId, Long cncId);

    List<Task> getAllTasksForMachine(Long machineId);

//    Task startTaskWorkTime(Long taskId);
//
//    Task endTaskWorkTime(Long taskId);

    Task completeTask(Long taskId);

    boolean checkIfSlotIsAvailable(DateTimeDTO dateTimeDTO);

    LocalDate findFirstAvailableSlot(Long machineId);

    List<Task> getAllTasksForEmployee(Long employeeId);

}
