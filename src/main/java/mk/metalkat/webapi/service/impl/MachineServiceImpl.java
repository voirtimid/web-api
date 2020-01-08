package mk.metalkat.webapi.service.impl;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.models.Machine;
import mk.metalkat.webapi.repository.MachineRepository;
import mk.metalkat.webapi.service.MachineService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MachineServiceImpl implements MachineService {

    private final MachineRepository machineRepository;

    @Override
    public Machine save(Machine machine) {
        return machineRepository.save(machine);
    }

    @Override
    public Machine getById(Long machineId) {
        Optional<Machine> optionalMachine = machineRepository.findById(machineId);

        if (optionalMachine.isPresent()) {
            Machine machine = optionalMachine.get();
            return machine;
        }
        return null;
    }

    @Override
    public Machine update(Long machineId, Machine machine) {
        if (machineId.equals(machine.getMachineId())) {
            Optional<Machine> optionalMachine = machineRepository.findById(machine.getMachineId());

            if (optionalMachine.isPresent()) {
                Machine prevMachine = optionalMachine.get();
                prevMachine.setName(machine.getName());
                prevMachine.setDescription(machine.getDescription());
                prevMachine.setShortName(machine.getShortName());
                return machineRepository.save(prevMachine);
            }
        }
        return null;
    }

    @Override
    public Machine delete(Machine machine) {
        Optional<Machine> optionalMachine = machineRepository.findById(machine.getMachineId());

        if (optionalMachine.isPresent()) {
            machineRepository.delete(machine);
            return optionalMachine.get();
        }

        return null;
    }

    @Override
    public List<Machine> getAll() {
        return machineRepository.findAll();
    }
}
