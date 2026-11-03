package ro.ubm.paw.medmonitor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.ubm.paw.medmonitor.dto.PacientResponse;
import ro.ubm.paw.medmonitor.service.PacientService;

import java.util.List;

@RestController
@RequestMapping("/api/medici/{medicId}/pacienti")
public class PacientController {

    private final PacientService pacientService;

    public PacientController(PacientService pacientService) {
        this.pacientService = pacientService;
    }

    @GetMapping
    public List<PacientResponse> aiMedicului(@PathVariable Long medicId) {
        return pacientService.aiMedicului(medicId);
    }
}
