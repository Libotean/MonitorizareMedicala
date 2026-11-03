package ro.ubm.paw.medmonitor.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ro.ubm.paw.medmonitor.dto.PacientResponse;
import ro.ubm.paw.medmonitor.repository.MedicRepository;
import ro.ubm.paw.medmonitor.repository.PacientRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PacientService {

    private final PacientRepository pacientRepository;
    private final MedicRepository medicRepository;

    public PacientService(PacientRepository pacientRepository, MedicRepository medicRepository) {
        this.pacientRepository = pacientRepository;
        this.medicRepository = medicRepository;
    }

    public List<PacientResponse> aiMedicului(Long medicId) {
        if (!medicRepository.existsById(medicId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Medicul nu exista");
        }
        return pacientRepository.findByMedicId(medicId).stream()
                .map(PacientResponse::din)
                .toList();
    }
}
