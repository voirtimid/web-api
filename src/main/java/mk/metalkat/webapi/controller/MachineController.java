package mk.metalkat.webapi.controller;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.models.jpa.Machine;
import mk.metalkat.webapi.service.MachineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/machines")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
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

    @PutMapping(value = "/{machineId}")
    public Machine updateMachine(@PathVariable("machineId") Long machineId, @RequestBody Machine machine) {
        return machineService.update(machineId, machine);
    }

    @DeleteMapping(value = "/{machineId}")
    public Machine deleteMachine(@PathVariable Long machineId) {
        return machineService.delete(machineId);
    }

    @GetMapping
    public List<Machine> getAllMachines() {
        return machineService.getAll();
    }
}
