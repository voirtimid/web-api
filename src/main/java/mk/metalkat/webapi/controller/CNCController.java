package mk.metalkat.webapi.controller;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.models.CNC;
import mk.metalkat.webapi.service.CNCService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/cncs")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class CNCController {

    private final CNCService cncService;

    @GetMapping(value = "/{cncId}")
    public CNC getCncById(@PathVariable("cncId") Long cncId) {
        return cncService.getCNC(cncId);
    }

    @PostMapping
    public CNC createNewCnc(@RequestBody CNC cnc) {
        return cncService.save(cnc);
    }

    @PutMapping(value = "/{cncId}")
    public CNC updateCnc(@PathVariable("cncId") Long cncId, @RequestBody CNC cnc) {
        return cncService.update(cncId, cnc);
    }

    @DeleteMapping(value = "/{cncId}")
    public CNC deleteCnc(@PathVariable Long cncId) {
        return cncService.delete(cncId);
    }

    @GetMapping
    public List<CNC> getAllCncs() {
        return cncService.getAll();
    }


}
