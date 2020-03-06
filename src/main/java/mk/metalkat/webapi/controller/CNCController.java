package mk.metalkat.webapi.controller;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.models.jpa.Cnc;
import mk.metalkat.webapi.service.CNCService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/cncs")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.43.211:3000"})
public class CNCController {

    private final CNCService cncService;

    @GetMapping(value = "/{cncId}")
    public Cnc getCncById(@PathVariable("cncId") Long cncId) {
        return cncService.getCNC(cncId);
    }

    @GetMapping(value = "/file/{fileName}")
    public Cnc findByFileName(@PathVariable("fileName") String fileName) {
        return cncService.findByFileName(fileName);
    }

    @PostMapping
    public Cnc createNewCnc(@RequestBody Cnc cnc) {
        return cncService.save(cnc);
    }

    @PutMapping(value = "/{cncId}")
    public Cnc updateCnc(@PathVariable("cncId") Long cncId, @RequestBody Cnc cnc) {
        return cncService.update(cncId, cnc);
    }

    @DeleteMapping(value = "/{cncId}")
    public Cnc deleteCnc(@PathVariable Long cncId) {
        return cncService.delete(cncId);
    }

    @GetMapping
    public List<Cnc> getAllCncs() {
        return cncService.getAll();
    }


}
