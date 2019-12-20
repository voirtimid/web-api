package mk.metalkat.webapi.controller;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.models.Machine;
import mk.metalkat.webapi.service.MachineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/machines")
@RequiredArgsConstructor
public class MachineController {

    private final MachineService machineService;

    @GetMapping(value = "/{machineId}")
    public Machine getMachineById(@PathVariable("machineId") Long machineId) {
        return machineService.getById(machineId);
    }

    @PostMapping
    public Machine createNewMachine(@RequestBody Machine machine) {
        return machineService.save(machine);
    }

    @PutMapping
    public Machine updateMachine(@RequestBody Machine machine) {
        return machineService.update(machine);
    }

    @DeleteMapping
    public void deleteMachine(@RequestBody Machine machine) {
        machineService.delete(machine);
    }

    @GetMapping
    public List<Machine> getAllMachines() {
        return machineService.getAll();
    }
}
