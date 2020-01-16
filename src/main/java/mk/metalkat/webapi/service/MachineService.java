package mk.metalkat.webapi.service;

import mk.metalkat.webapi.models.Machine;

import javax.crypto.Mac;
import java.util.List;

public interface MachineService {

    Machine getById(Long machineId);

    Machine save(Machine machine);

    Machine update(Long machineId, Machine machine);

    Machine delete(Long machineId);

    List<Machine> getAll();
}
