package ro.ubm.paw.medmonitor.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ro.ubm.paw.medmonitor.dto.AlertaResponse;
import ro.ubm.paw.medmonitor.model.Alerta;
import ro.ubm.paw.medmonitor.repository.AlertaRepository;
import ro.ubm.paw.medmonitor.repository.MedicRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AlertaService {

    private final AlertaRepository alertaRepository;
    private final MedicRepository medicRepository;

    public AlertaService(AlertaRepository alertaRepository, MedicRepository medicRepository) {
        this.alertaRepository = alertaRepository;
        this.medicRepository = medicRepository;
    }

    public List<AlertaResponse> necitite(Long medicId) {
        if (!medicRepository.existsById(medicId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Medicul nu exista");
        }
        return alertaRepository.findNecititeByMedicId(medicId).stream()
                .map(AlertaResponse::din)
                .toList();
    }

    // Fara save(): Hibernate face UPDATE la finalul tranzactiei (dirty checking)
    @Transactional
    public void marcheazaCitita(Long id) {
        Alerta alerta = alertaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Alerta nu exista"));
        alerta.setCitita(true);
    }
}
