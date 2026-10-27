package ro.ubm.paw.medmonitor.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "medic")
public class Medic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "utilizator_id", nullable = false, unique = true)
    private Utilizator utilizator;

    @Column(nullable = false, length = 100)
    private String nume;

    @Column(nullable = false, length = 100)
    private String prenume;

    @Column(nullable = false, length = 100)
    private String specializare;

    @ManyToMany
    @JoinTable(name = "medic_pacient",
            joinColumns = @JoinColumn(name = "medic_id"),
            inverseJoinColumns = @JoinColumn(name = "pacient_id"))
    private Set<Pacient> pacienti = new HashSet<>();

    protected Medic() { }

    public Medic(Utilizator utilizator, String nume, String prenume, String specializare) {
        this.utilizator = utilizator;
        this.nume = nume;
        this.prenume = prenume;
        this.specializare = specializare;
    }

    public Long getId() { return id; }

    public Utilizator getUtilizator() { return utilizator; }

    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }

    public String getPrenume() { return prenume; }
    public void setPrenume(String prenume) { this.prenume = prenume; }

    public String getSpecializare() { return specializare; }
    public void setSpecializare(String specializare) { this.specializare = specializare; }

    public Set<Pacient> getPacienti() { return pacienti; }
}
