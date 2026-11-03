package ro.ubm.paw.medmonitor.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ro.ubm.paw.medmonitor.dto.MasuratoareRequest;
import ro.ubm.paw.medmonitor.dto.MasuratoareResponse;
import ro.ubm.paw.medmonitor.service.MasuratoareService;

import java.util.List;

@RestController
@RequestMapping("/api/pacienti/{pacientId}/masuratori")
public class MasuratoareController {

    private final MasuratoareService masuratoareService;

    public MasuratoareController(MasuratoareService masuratoareService) {
        this.masuratoareService = masuratoareService;
    }

    @GetMapping
    public List<MasuratoareResponse> istoric(@PathVariable Long pacientId) {
        return masuratoareService.istoric(pacientId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MasuratoareResponse adauga(@PathVariable Long pacientId, @Valid @RequestBody MasuratoareRequest request) {
        return masuratoareService.adauga(pacientId, request);
    }
}
