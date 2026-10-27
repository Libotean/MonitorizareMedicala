package ro.ubm.paw.medmonitor.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "alerta")
public class Alerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "masuratoare_id")
    private Masuratoare masuratoare;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipAlerta tip;

    @Column(nullable = false, length = 255)
    private String mesaj;

    @Column(nullable = false)
    private boolean citita;

    @Column(name = "creat_la", nullable = false, updatable = false)
    private LocalDateTime creatLa;

    protected Alerta() { }

    public Alerta(Masuratoare masuratoare, TipAlerta tip, String mesaj) {
        this.masuratoare = masuratoare;
        this.tip = tip;
        this.mesaj = mesaj;
    }

    @PrePersist
    void laCreare() {
        creatLa = LocalDateTime.now();
    }

    public Long getId() { return id; }

    public Masuratoare getMasuratoare() { return masuratoare; }

    public TipAlerta getTip() { return tip; }

    public String getMesaj() { return mesaj; }

    public boolean isCitita() { return citita; }
    public void setCitita(boolean citita) { this.citita = citita; }

    public LocalDateTime getCreatLa() { return creatLa; }
}
