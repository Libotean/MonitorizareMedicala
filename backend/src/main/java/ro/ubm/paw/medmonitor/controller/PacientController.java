package ro.ubm.paw.medmonitor.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.ubm.paw.medmonitor.dto.PacientResponse;
import ro.ubm.paw.medmonitor.service.AccesService;
import ro.ubm.paw.medmonitor.service.PacientService;

import java.util.List;

@RestController
@RequestMapping("/api/medici/{medicId}/pacienti")
public class PacientController {

    private final PacientService pacientService;
    private final AccesService accesService;

    public PacientController(PacientService pacientService, AccesService accesService) {
        this.pacientService = pacientService;
        this.accesService = accesService;
    }

    @GetMapping
    public List<PacientResponse> aiMedicului(@PathVariable Long medicId, @AuthenticationPrincipal String email) {
        accesService.verificaMedic(medicId, email);
        return pacientService.aiMedicului(medicId);
    }
}
