package ro.ubm.paw.medmonitor.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ro.ubm.paw.medmonitor.dto.LoginRequest;
import ro.ubm.paw.medmonitor.dto.RegisterRequest;
import ro.ubm.paw.medmonitor.dto.TokenResponse;
import ro.ubm.paw.medmonitor.model.Pacient;
import ro.ubm.paw.medmonitor.model.Rol;
import ro.ubm.paw.medmonitor.model.Utilizator;
import ro.ubm.paw.medmonitor.repository.PacientRepository;
import ro.ubm.paw.medmonitor.repository.UtilizatorRepository;

@Service
@Transactional(readOnly = true)
public class AuthService {

    private final UtilizatorRepository utilizatorRepository;
    private final PacientRepository pacientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UtilizatorRepository utilizatorRepository,
                       PacientRepository pacientRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.utilizatorRepository = utilizatorRepository;
        this.pacientRepository = pacientRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public TokenResponse login(LoginRequest request) {
        // acelasi mesaj pentru email inexistent si parola gresita (nu dezvaluim ce email-uri exista)
        Utilizator utilizator = utilizatorRepository.findByEmail(request.email())
                .filter(u -> passwordEncoder.matches(request.parola(), u.getParola()))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Email sau parola gresita"));
        return new TokenResponse(jwtService.genereaza(utilizator), utilizator.getRol().name());
    }

    // doar pacientii se inregistreaza singuri; rolul nu vine din request
    @Transactional
    public TokenResponse register(RegisterRequest request) {
        if (utilizatorRepository.existsByEmail(request.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email-ul este deja folosit");
        }
        Utilizator utilizator = utilizatorRepository.save(
                new Utilizator(request.email(), passwordEncoder.encode(request.parola()), Rol.PACIENT));
        pacientRepository.save(
                new Pacient(utilizator, request.nume(), request.prenume(), request.dataNasterii()));
        return new TokenResponse(jwtService.genereaza(utilizator), utilizator.getRol().name());
    }
}
