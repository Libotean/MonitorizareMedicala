package ro.ubm.paw.medmonitor.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ro.ubm.paw.medmonitor.dto.MasuratoareRequest;
import ro.ubm.paw.medmonitor.dto.MasuratoareResponse;
import ro.ubm.paw.medmonitor.service.AccesService;
import ro.ubm.paw.medmonitor.service.MasuratoareService;

import java.util.List;

@RestController
@RequestMapping("/api/pacienti/{pacientId}/masuratori")
public class MasuratoareController {

    private final MasuratoareService masuratoareService;
    private final AccesService accesService;

    public MasuratoareController(MasuratoareService masuratoareService, AccesService accesService) {
        this.masuratoareService = masuratoareService;
        this.accesService = accesService;
    }

    @GetMapping
    public List<MasuratoareResponse> istoric(@PathVariable Long pacientId, @AuthenticationPrincipal String email) {
        accesService.verificaPacient(pacientId, email);
        return masuratoareService.istoric(pacientId);
    }

    // POST e permis doar rolului PACIENT (SecurityConfig), deci aici trece doar pacientul insusi
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MasuratoareResponse adauga(@PathVariable Long pacientId, @AuthenticationPrincipal String email,
                                      @Valid @RequestBody MasuratoareRequest request) {
        accesService.verificaPacient(pacientId, email);
        return masuratoareService.adauga(pacientId, request);
    }
}
