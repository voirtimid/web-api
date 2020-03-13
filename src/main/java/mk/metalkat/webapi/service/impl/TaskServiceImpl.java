package mk.metalkat.webapi.service.impl;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.exceptions.ModelNotFoundException;
import mk.metalkat.webapi.models.dto.LocalDateDTO;
import mk.metalkat.webapi.models.dto.TaskDTO;
import mk.metalkat.webapi.models.enums.Status;
import mk.metalkat.webapi.models.jpa.*;
import mk.metalkat.webapi.repository.*;
import mk.metalkat.webapi.service.TaskService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        Job updatedJob = jobRepository.findById(taskDTO.getJobId()).orElseThrow(() -> new ModelNotFoundException("The job does not exist"));

        task.setJob(updatedJob);
        Employee employee = employeeRepository.findById(taskDTO.getEmployeeId()).orElseThrow(() -> new ModelNotFoundException("The employee does not exist"));
        task.setEmployee(employee);
        Machine machine = machineRepository.findById(taskDTO.getMachineId()).orElseThrow(() -> new ModelNotFoundException("The machine does not exist"));
        task.setMachine(machine);
        Cnc cncCode = cncRepository.findById(taskDTO.getCncCodeId()).orElseThrow(() -> new ModelNotFoundException("The cncCode does not exist"));
        task.setCncCode(cncCode);

        int size = updatedJob.getTasks().size();
        task.setTaskName(updatedJob.getJobName() + " Task " + (size + 1));

        LocalDate plannedStartDate = task.getPlannedStartDate();
        if (plannedStartDate.isEqual(LocalDate.now())) {
            task.setStatus(Status.TODAY);
        }

        return taskRepository.save(task);
    }

    @Override
    public Task updateTask_v2(TaskDTO taskDTO) {
        Task task = taskDTO.getTask();
        Job job = jobRepository.findById(taskDTO.getJobId()).orElseThrow(() -> new ModelNotFoundException("The job does not exist"));

        Double totalWorkTime = task.getTotalWorkTime();
        Double realMinutesForPiece = task.getRealMinutesForPiece();

        Double jobTotalMinutesForPiece = job.getTasks().stream().filter(task1 -> !task1.getTaskId().equals(task.getTaskId())).map(Task::getRealMinutesForPiece).reduce((Double::sum)).orElse(0d);
        Double jobTotalWorkTime = job.getTasks().stream().filter(task1 -> !task1.getTaskId().equals(task.getTaskId())).map(Task::getTotalWorkTime).reduce((Double::sum)).orElse(0d);

        job.setRealHours(jobTotalWorkTime + totalWorkTime);
        job.setRealTimeForPiece(jobTotalMinutesForPiece + realMinutesForPiece);

        Job updatedJob = jobRepository.saveAndFlush(job);
        task.setJob(updatedJob);


        LocalDate plannedStartDate = task.getPlannedStartDate();
        LocalDate now = LocalDate.now();

        if (task.getRealEndDate() == null) {
            if (task.getRealStartDate() != null) {
                LocalDate plannedEndDate = task.getPlannedEndDate();
                if (!plannedEndDate.isBefore(now)) {
                    task.setStatus(Status.NORMAL);
                } else {
                    task.setStatus(Status.BEHIND);
                }
            } else {
                if (plannedStartDate.isBefore(now)) {
                    task.setStatus(Status.BEHIND);
                } else {
                    task.setStatus(Status.NORMAL);
                }
            }
        }

        return taskRepository.saveAndFlush(task);
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
            Job job = task.getJob();
            job.removeTask(task);
            job.setPlannedTimeForPiece(job.getPlannedTimeForPiece() - task.getMinutesForPiece());
            job.setPlannedHours(job.getPlannedHours() - task.getPlannedHours());
            job.setRealHours(job.getRealHours() - task.getRealMinutesForPiece());
            job.setRealTimeForPiece(job.getRealTimeForPiece() - task.getRealMinutesForPiece());
            jobRepository.save(job);
            taskRepository.delete(task);
            return task;
        }

        return null;
    }

    @Override
    public List<Task> getAllTasks() {
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

    @Override
    public List<Task> getAllTasksForMachine(Long machineId) {
        return taskRepository.findAll().stream()
                .filter(task -> !task.isFinished())
                .filter(task -> task.getMachine().getMachineId().equals(machineId))
                .collect(Collectors.toList());
    }

//    @Override
//    public Task startTaskWorkTime(Long taskId) {
//        Task task = getTask(taskId);
//        task.setStartWorkTime(LocalTime.now());
//        task.setWorkInProgress(true);
//        return updateTask(taskId, task);
//    }
//
//    @Override
//    public Task endTaskWorkTime(Long taskId) {
//        Task task = getTask(taskId);
//        task.setEndWorkTime(LocalTime.now());
//        task.setWorkInProgress(false);
//
//        long startWorkTime = task.getStartWorkTime().toNanoOfDay();
//        long endWorkTime = task.getEndWorkTime().toNanoOfDay();
//
//        task.setTotalWorkTime(task.getTotalWorkTime() + endWorkTime - startWorkTime);
//        return updateTask(taskId, task);
//    }
//
    @Override
    public Task completeTask(Long taskId) {
        Task task = getTask(taskId);
        task.setFinished(true);
        task.setStatus(Status.FINISHED);
        return updateTask(taskId, task);
    }

    @Override
    public boolean checkIfSlotIsAvailable(LocalDateDTO localDateDTO) {
        List<Task> allTasks = taskRepository.findAll();
        if (allTasks.isEmpty()) {
            return true;
        }
        LocalDate startDate = localDateDTO.getStartDate();
        return allTasks.stream().allMatch(task -> task.getPlannedEndDate().isBefore(startDate));
    }

    @Override
    public LocalDate findFirstAvailableSlot(Long machineId) {
        List<Task> allTasksForMachine = getAllTasksForMachine(machineId);
        if (allTasksForMachine.isEmpty()) {
            return LocalDate.now();
        }
        return allTasksForMachine.stream()
                .map(Task::getPlannedEndDate)
                .max(Comparator.naturalOrder()).get().plusDays(1);
    }

    @Override
    public List<Task> getAllTasksForEmployee(Long employeeId) {
        return taskRepository.findAll().stream()
                .filter(task -> !task.isFinished())
                .filter(task -> task.getEmployee().getEmployeeId().equals(employeeId))
                .collect(Collectors.toList());
    }
}
