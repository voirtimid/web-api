package mk.metalkat.webapi.service.impl;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.models.CNC;
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
    public CNC getCNC(Long id) {
        return cncRepository.findById(id).orElse(null);
    }

    @Override
    public CNC save(CNC cnc) {
        return cncRepository.save(cnc);
    }

    @Override
    public CNC update(Long cncId, CNC cnc) {
        if (!cncRepository.findById(cncId).isPresent() || !cncId.equals(cnc.getCncId())) {
            return null;
        }

        return cncRepository.save(cnc);
    }

    @Override
    public CNC delete(Long cncId) {
        Optional<CNC> optionCNC = cncRepository.findById(cncId);
        if (optionCNC.isPresent()) {
            CNC cnc = optionCNC.get();
            cncRepository.delete(cnc);
            return cnc;
        }
        return null;
    }

    @Override
    public List<CNC> getAll() {
        return cncRepository.findAll();
    }
}
