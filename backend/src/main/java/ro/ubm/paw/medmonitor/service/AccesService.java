package ro.ubm.paw.medmonitor.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ro.ubm.paw.medmonitor.repository.AlertaRepository;
import ro.ubm.paw.medmonitor.repository.MedicRepository;
import ro.ubm.paw.medmonitor.repository.PacientRepository;

// verifica daca utilizatorul logat (email din token) are voie la resursa ceruta
@Service
@Transactional(readOnly = true)
public class AccesService {

    private final MedicRepository medicRepository;
    private final PacientRepository pacientRepository;
    private final AlertaRepository alertaRepository;

    public AccesService(MedicRepository medicRepository, PacientRepository pacientRepository,
                        AlertaRepository alertaRepository) {
        this.medicRepository = medicRepository;
        this.pacientRepository = pacientRepository;
        this.alertaRepository = alertaRepository;
    }

    public void verificaMedic(Long medicId, String email) {
        if (!medicRepository.existsByIdAndUtilizatorEmail(medicId, email)) throw interzis();
    }

    // pacientul insusi sau un medic care il urmareste
    public void verificaPacient(Long pacientId, String email) {
        if (!pacientRepository.existsByIdAndUtilizatorEmail(pacientId, email)
                && !medicRepository.urmarestePacient(email, pacientId)) throw interzis();
    }

    public void verificaAlerta(Long alertaId, String email) {
        if (!alertaRepository.apartineMedicului(alertaId, email)) throw interzis();
    }

    private ResponseStatusException interzis() {
        return new ResponseStatusException(HttpStatus.FORBIDDEN, "Acces interzis");
    }
}
