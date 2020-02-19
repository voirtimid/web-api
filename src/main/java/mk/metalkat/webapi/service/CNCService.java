package mk.metalkat.webapi.service;

import mk.metalkat.webapi.models.Cnc;

import java.util.List;

public interface CNCService {

    Cnc getCNC(Long id);

    Cnc findByFileName(String fileName);

    Cnc save(Cnc cnc);

    Cnc update(Long cncId, Cnc cnc);

    Cnc delete(Long cncId);

    List<Cnc> getAll();
}
