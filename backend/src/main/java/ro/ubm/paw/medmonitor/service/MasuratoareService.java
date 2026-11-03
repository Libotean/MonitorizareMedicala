package ro.ubm.paw.medmonitor.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ro.ubm.paw.medmonitor.dto.MasuratoareResponse;
import ro.ubm.paw.medmonitor.repository.MasuratoareRepository;
import ro.ubm.paw.medmonitor.repository.PacientRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MasuratoareService {

    private final MasuratoareRepository masuratoareRepository;
    private final PacientRepository pacientRepository;

    public MasuratoareService(MasuratoareRepository masuratoareRepository, PacientRepository pacientRepository) {
        this.masuratoareRepository = masuratoareRepository;
        this.pacientRepository = pacientRepository;
    }

    public List<MasuratoareResponse> istoric(Long pacientId) {
        if (!pacientRepository.existsById(pacientId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pacientul nu exista");
        }
        return masuratoareRepository.findByPacientIdOrderByMasuratLaDesc(pacientId)
                .stream()
                .map(MasuratoareResponse::din)
                .toList();
    }
}
