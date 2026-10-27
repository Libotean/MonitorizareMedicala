package ro.ubm.paw.medmonitor.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "utilizator")
public class Utilizator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    // hash BCrypt, niciodata parola in clar
    @Column(nullable = false, length = 100)
    private String parola;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Rol rol;

    @Column(name = "creat_la", nullable = false, updatable = false)
    private LocalDateTime creatLa;

    protected Utilizator() { }

    public Utilizator(String email, String parola, Rol rol) {
        this.email = email;
        this.parola = parola;
        this.rol = rol;
    }

    @PrePersist
    void laCreare() {
        creatLa = LocalDateTime.now();
    }

    public Long getId() { return id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getParola() { return parola; }
    public void setParola(String parola) { this.parola = parola; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public LocalDateTime getCreatLa() { return creatLa; }
}
