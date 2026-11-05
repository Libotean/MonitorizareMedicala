package ro.ubm.paw.medmonitor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ro.ubm.paw.medmonitor.dto.AlertaResponse;
import ro.ubm.paw.medmonitor.service.AccesService;
import ro.ubm.paw.medmonitor.service.AlertaService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AlertaController {

    private final AlertaService alertaService;
    private final AccesService accesService;

    public AlertaController(AlertaService alertaService, AccesService accesService) {
        this.alertaService = alertaService;
        this.accesService = accesService;
    }

    @GetMapping("/medici/{medicId}/alerte")
    public List<AlertaResponse> necitite(@PathVariable Long medicId, @AuthenticationPrincipal String email) {
        accesService.verificaMedic(medicId, email);
        return alertaService.necitite(medicId);
    }

    @PatchMapping("/alerte/{id}/citita")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void marcheazaCitita(@PathVariable Long id, @AuthenticationPrincipal String email) {
        accesService.verificaAlerta(id, email);
        alertaService.marcheazaCitita(id);
    }
}
