package ro.ubm.paw.medmonitor.dto;

import ro.ubm.paw.medmonitor.model.Alerta;
import ro.ubm.paw.medmonitor.model.Pacient;
import ro.ubm.paw.medmonitor.model.TipAlerta;

import java.time.LocalDateTime;

public record AlertaResponse(
        Long id, TipAlerta tip, String mesaj, boolean citita, LocalDateTime creatLa,
        Long masuratoareId, Long pacientId, String pacientNume
) {
    public static AlertaResponse din(Alerta a) {
        Pacient p = a.getMasuratoare().getPacient();
        return new AlertaResponse(a.getId(), a.getTip(), a.getMesaj(), a.isCitita(), a.getCreatLa(),
                a.getMasuratoare().getId(), p.getId(), p.getPrenume() + " " + p.getNume());
    }
}
