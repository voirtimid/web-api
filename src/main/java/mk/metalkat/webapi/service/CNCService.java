package mk.metalkat.webapi.service;

import mk.metalkat.webapi.models.CNC;

import java.util.List;

public interface CNCService {

    CNC getCNC(Long id);

    CNC save(CNC cnc);

    CNC update(Long cncId, CNC cnc);

    CNC delete(Long cncId);

    List<CNC> getAll();
}
