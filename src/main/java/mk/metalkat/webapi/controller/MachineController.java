package mk.metalkat.webapi.controller;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.models.Machine;
import mk.metalkat.webapi.service.MachineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/machines")
@RequiredArgsConstructor
public class MachineController {

    private final MachineService machineService;

    @GetMapping(value = "/{machineId}")
    public ResponseEntity<Machine> getMachineById(@PathVariable("machineId") Long machineId) {
        return ResponseEntity.ok(machineService.getById(machineId));
    }

    @PostMapping
    public Machine createNewMachine(@RequestBody Machine machine) {

        return machineService.save(machine);
    }

    @PutMapping(value = "/{machineId}")
    public ResponseEntity<Machine> updateMachine(@PathVariable("machineId") Long machineId, @RequestBody Machine machine) {
        Machine updateMachine = machineService.update(machineId, machine);
        if (updateMachine == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(updateMachine);
    }

    @DeleteMapping
    public Machine deleteMachine(@RequestBody Machine machine) {
        return machineService.delete(machine);
    }

    @GetMapping
    public List<Machine> getAllMachines() {
        return machineService.getAll();
    }
}
