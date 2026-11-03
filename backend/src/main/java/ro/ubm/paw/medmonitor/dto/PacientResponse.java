package ro.ubm.paw.medmonitor.dto;

import ro.ubm.paw.medmonitor.model.Pacient;

import java.time.LocalDate;

public record PacientResponse(Long id, String nume, String prenume, LocalDate dataNasterii) {
    public static PacientResponse din(Pacient p) {
        return new PacientResponse(p.getId(), p.getNume(), p.getPrenume(), p.getDataNasterii());
    }
}
