package ro.ubm.paw.medmonitor.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "pacient")
public class Pacient {

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

    @Column(name = "data_nasterii", nullable = false)
    private LocalDate dataNasterii;

    protected Pacient() { }

    public Pacient(Utilizator utilizator, String nume, String prenume, LocalDate dataNasterii) {
        this.utilizator = utilizator;
        this.nume = nume;
        this.prenume = prenume;
        this.dataNasterii = dataNasterii;
    }

    public Long getId() { return id; }

    public Utilizator getUtilizator() { return utilizator; }

    public String getNume() { return nume; }
    public void setNume(String nume) { this.nume = nume; }

    public String getPrenume() { return prenume; }
    public void setPrenume(String prenume) { this.prenume = prenume; }

    public LocalDate getDataNasterii() { return dataNasterii; }
    public void setDataNasterii(LocalDate dataNasterii) { this.dataNasterii = dataNasterii; }
}
