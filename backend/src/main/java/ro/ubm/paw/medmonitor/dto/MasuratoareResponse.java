package ro.ubm.paw.medmonitor.dto;

import ro.ubm.paw.medmonitor.model.Masuratoare;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MasuratoareResponse(
        Long id,
        Integer puls,
        Integer tensiuneSistolica,
        Integer tensiuneDiastolica,
        BigDecimal temperatura,
        Integer glicemie,
        LocalDateTime masuratLa
) {
    public static MasuratoareResponse din(Masuratoare m) {
        return new MasuratoareResponse(m.getId(), m.getPuls(), m.getTensiuneSistolica(),
                m.getTensiuneDiastolica(), m.getTemperatura(), m.getGlicemie(), m.getMasuratLa());
    }
}
