package mk.metalkat.webapi.service.impl;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.exceptions.ModelNotFoundException;
import mk.metalkat.webapi.models.jpa.Cnc;
import mk.metalkat.webapi.repository.CNCRepository;
import mk.metalkat.webapi.service.CNCService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CNCServiceImpl implements CNCService {

    private final CNCRepository cncRepository;

    @Override
    public Cnc getCNC(Long id) {
        return cncRepository.findById(id).orElseThrow(() -> new ModelNotFoundException("Cnc code does not exist!"));
    }

    @Override
    public Cnc findByFileName(String fileName) {
        return cncRepository.findByCncFilenameEquals(fileName);
    }

    @Override
    public Cnc save(Cnc cnc) {
        return cncRepository.save(cnc);
    }

    @Override
    public Cnc update(Long cncId, Cnc cnc) {
        if (!cncRepository.findById(cncId).isPresent() || !cncId.equals(cnc.getCncId())) {
            throw new ModelNotFoundException("Cnc code does not exist!");
        }
        return cncRepository.save(cnc);
    }

    @Override
    public Cnc delete(Long cncId) {
        Optional<Cnc> optionCNC = cncRepository.findById(cncId);
        if (optionCNC.isPresent()) {
            Cnc cnc = optionCNC.get();
            cncRepository.delete(cnc);
            return cnc;
        }
        throw new ModelNotFoundException("Cnc code does not exist!");
    }

    @Override
    public List<Cnc> getAll() {
        return cncRepository.findAll();
    }
}
