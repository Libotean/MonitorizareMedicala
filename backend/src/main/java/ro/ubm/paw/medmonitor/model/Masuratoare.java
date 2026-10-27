package ro.ubm.paw.medmonitor.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "masuratoare")
public class Masuratoare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pacient_id")
    private Pacient pacient;

    // Toate valorile sunt optionale (Integer/BigDecimal, nu int): o masuratoare poate avea doar pulsul
    private Integer puls;

    @Column(name = "tensiune_sistolica")
    private Integer tensiuneSistolica;

    @Column(name = "tensiune_diastolica")
    private Integer tensiuneDiastolica;

    @Column(precision = 4, scale = 1)
    private BigDecimal temperatura;

    private Integer glicemie;

    @Column(name = "masurat_la", nullable = false)
    private LocalDateTime masuratLa;

    protected Masuratoare() { }

    public Masuratoare(Pacient pacient, Integer puls, Integer tensiuneSistolica, Integer tensiuneDiastolica,
                       BigDecimal temperatura, Integer glicemie, LocalDateTime masuratLa) {
        this.pacient = pacient;
        this.puls = puls;
        this.tensiuneSistolica = tensiuneSistolica;
        this.tensiuneDiastolica = tensiuneDiastolica;
        this.temperatura = temperatura;
        this.glicemie = glicemie;
        this.masuratLa = masuratLa;
    }

    // Daca pacientul nu a dat ora masuratorii, folosim momentul salvarii
    @PrePersist
    void laCreare() {
        if (masuratLa == null) {
            masuratLa = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }

    public Pacient getPacient() { return pacient; }

    public Integer getPuls() { return puls; }
    public void setPuls(Integer puls) { this.puls = puls; }

    public Integer getTensiuneSistolica() { return tensiuneSistolica; }
    public void setTensiuneSistolica(Integer tensiuneSistolica) { this.tensiuneSistolica = tensiuneSistolica; }

    public Integer getTensiuneDiastolica() { return tensiuneDiastolica; }
    public void setTensiuneDiastolica(Integer tensiuneDiastolica) { this.tensiuneDiastolica = tensiuneDiastolica; }

    public BigDecimal getTemperatura() { return temperatura; }
    public void setTemperatura(BigDecimal temperatura) { this.temperatura = temperatura; }

    public Integer getGlicemie() { return glicemie; }
    public void setGlicemie(Integer glicemie) { this.glicemie = glicemie; }

    public LocalDateTime getMasuratLa() { return masuratLa; }
    public void setMasuratLa(LocalDateTime masuratLa) { this.masuratLa = masuratLa; }
}
