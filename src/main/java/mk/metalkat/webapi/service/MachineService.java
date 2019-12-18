package mk.metalkat.webapi.service;

import mk.metalkat.webapi.models.Machine;

import java.util.List;

public interface MachineService {

    Machine getById(Long machineId);

    Machine save(Machine machine);

    Machine update(Machine machine);

    void delete(Machine machine);

    List<Machine> getAll();


}
