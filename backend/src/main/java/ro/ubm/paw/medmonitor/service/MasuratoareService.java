package ro.ubm.paw.medmonitor.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ro.ubm.paw.medmonitor.dto.MasuratoareRequest;
import ro.ubm.paw.medmonitor.dto.MasuratoareResponse;
import ro.ubm.paw.medmonitor.model.Alerta;
import ro.ubm.paw.medmonitor.model.Masuratoare;
import ro.ubm.paw.medmonitor.model.Pacient;
import ro.ubm.paw.medmonitor.model.TipAlerta;
import ro.ubm.paw.medmonitor.repository.AlertaRepository;
import ro.ubm.paw.medmonitor.repository.MasuratoareRepository;
import ro.ubm.paw.medmonitor.repository.PacientRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MasuratoareService {

    private final MasuratoareRepository masuratoareRepository;
    private final PacientRepository pacientRepository;
    private final AlertaRepository alertaRepository;

    public MasuratoareService(MasuratoareRepository masuratoareRepository, PacientRepository pacientRepository,
                              AlertaRepository alertaRepository) {
        this.masuratoareRepository = masuratoareRepository;
        this.pacientRepository = pacientRepository;
        this.alertaRepository = alertaRepository;
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

    @Transactional
    public MasuratoareResponse adauga(Long pacientId, MasuratoareRequest r) {
        Pacient pacient = pacientRepository.findById(pacientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pacientul nu exista"));
        Masuratoare m = masuratoareRepository.save(new Masuratoare(pacient, r.puls(), r.tensiuneSistolica(),
                r.tensiuneDiastolica(), r.temperatura(), r.glicemie(), r.masuratLa()));
        alertaRepository.saveAll(alerte(m));
        return MasuratoareResponse.din(m);
    }

    // Praguri stabilite
    private List<Alerta> alerte(Masuratoare m) {
        List<Alerta> alerte = new ArrayList<>();
        Integer puls = m.getPuls();
        if (puls != null && (puls < 60 || puls > 100)) {
            alerte.add(new Alerta(m, TipAlerta.PULS, "Puls anormal: " + puls + " bpm"));
        }
        Integer sis = m.getTensiuneSistolica();
        Integer dia = m.getTensiuneDiastolica();
        if ((sis != null && (sis >= 140 || sis < 90)) || (dia != null && dia >= 90)) {
            alerte.add(new Alerta(m, TipAlerta.TENSIUNE, "Tensiune anormala: "
                    + (sis != null ? sis : "-") + "/" + (dia != null ? dia : "-") + " mmHg"));
        }
        BigDecimal temp = m.getTemperatura();
        if (temp != null && (temp.compareTo(new BigDecimal("38.0")) >= 0 || temp.compareTo(new BigDecimal("35.0")) < 0)) {
            alerte.add(new Alerta(m, TipAlerta.TEMPERATURA, "Temperatura anormala: " + temp + " °C"));
        }
        Integer gli = m.getGlicemie();
        if (gli != null && (gli < 70 || gli > 180)) {
            alerte.add(new Alerta(m, TipAlerta.GLICEMIE, "Glicemie anormala: " + gli + " mg/dL"));
        }
        return alerte;
    }
}
