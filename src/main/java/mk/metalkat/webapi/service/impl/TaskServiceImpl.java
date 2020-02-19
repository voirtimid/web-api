package mk.metalkat.webapi.service.impl;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.exceptions.ModelNotFoundException;
import mk.metalkat.webapi.models.*;
import mk.metalkat.webapi.models.dto.TaskDTO;
import mk.metalkat.webapi.repository.*;
import mk.metalkat.webapi.service.TaskService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final MachineRepository machineRepository;

    private final EmployeeRepository employeeRepository;

    private final CNCRepository cncRepository;

    private final JobRepository jobRepository;

    @Override
    public Task getTask(Long taskId) {
        return taskRepository.findById(taskId).orElse(null);
    }

    @Override
    public Task createTask(TaskDTO taskDTO) {

        Task task = taskDTO.getTask();
        if (task.getTaskId() != null) {
            return null;
        }
        Job job = jobRepository.findById(taskDTO.getJobId()).orElseThrow(() -> new ModelNotFoundException("The job does not exist"));
        task.setJob(job);
        Employee employee = employeeRepository.findById(taskDTO.getEmployeeId()).orElseThrow(() -> new ModelNotFoundException("The employee does not exist"));
        task.setEmployee(employee);
        Machine machine = machineRepository.findById(taskDTO.getMachineId()).orElseThrow(() -> new ModelNotFoundException("The machine does not exist"));
        task.setMachine(machine);
        Cnc cncCode = cncRepository.findById(taskDTO.getCncCodeId()).orElseThrow(() -> new ModelNotFoundException("The cncCode does not exist"));
        task.setCncCode(cncCode);
        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(Long taskId, Task task) {
        if (!taskRepository.findById(task.getTaskId()).isPresent() || !taskId.equals(task.getTaskId())) {
            return null;
        }
        return taskRepository.save(task);
    }

    @Override
    public Task deleteTask(Long taskId) {
        Optional<Task> optionTask = taskRepository.findById(taskId);

        if (optionTask.isPresent()) {
            Task task = optionTask.get();
            taskRepository.delete(task);
            return task;
        }

        return null;
    }

    @Override
    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    @Override
    public Task setMachine(Long taskId, Long machineId) {
        Optional<Task> optionTask = taskRepository.findById(taskId);
        Optional<Machine> optionMachine = machineRepository.findById(machineId);

        if (optionTask.isPresent() && optionMachine.isPresent()) {
            Task task = optionTask.get();
            Machine machine = optionMachine.get();
            task.setMachine(machine);
            return taskRepository.save(task);
        }
        return null;
    }

    @Override
    public Task setEmployee(Long taskId, Long employeeId) {
        Optional<Task> optionTask = taskRepository.findById(taskId);
        Optional<Employee> optionEmployee = employeeRepository.findById(employeeId);

        if (optionTask.isPresent() && optionEmployee.isPresent()) {
            Task task = optionTask.get();
            Employee employee = optionEmployee.get();
            task.setEmployee(employee);
            return taskRepository.save(task);
        }
        return null;
    }

    @Override
    public Task setCncCode(Long taskId, Long cncId) {
        Optional<Task> optionTask = taskRepository.findById(taskId);
        Optional<Cnc> optionCncCode = cncRepository.findById(cncId);

        if (optionTask.isPresent() && optionCncCode.isPresent()) {
            Task task = optionTask.get();
            Cnc cnc = optionCncCode.get();
            task.setCncCode(cnc);
            return taskRepository.save(task);
        }
        return null;
    }
}
