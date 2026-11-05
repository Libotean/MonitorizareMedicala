package ro.ubm.paw.medmonitor.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ro.ubm.paw.medmonitor.model.Utilizator;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey cheie;
    private final long expirareMinute;

    public JwtService(@Value("${jwt.secret}") String secret,
                      @Value("${jwt.expirare-minute}") long expirareMinute) {
        this.cheie = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.expirareMinute = expirareMinute;
    }

    public String genereaza(Utilizator utilizator) {
        Instant acum = Instant.now();
        return Jwts.builder()
                .subject(utilizator.getEmail())
                .claim("rol", utilizator.getRol().name())
                .issuedAt(Date.from(acum))
                .expiration(Date.from(acum.plus(expirareMinute, ChronoUnit.MINUTES)))
                .signWith(cheie)
                .compact();
    }

    // arunca JwtException daca semnatura e falsa, tokenul e stricat sau a expirat
    public Claims valideaza(String token) {
        return Jwts.parser()
                .verifyWith(cheie)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
