package raf.lazar.diplomski_aorp.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import raf.lazar.diplomski_aorp.requests.PredmetCreateRequest;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Predmet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String naziv;
    @Column(nullable = false)
    private String tip_studija;
    @Column(nullable = false)
    private Integer espb;
    @Column(nullable = false)
    private Integer semestar;
    @Column(nullable = false)
    private Integer fond_predavanja;
    @Column(nullable = false)
    private Integer fond_vezbe;
    @Column(nullable = false)
    private Integer fond_praktikum;
    @Column(nullable = false)
    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Predavanje> predavanja = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Termini> termini = new ArrayList<>();

    public Predmet(PredmetCreateRequest request) {
        this.naziv = request.getNaziv();
        this.tip_studija = request.getTip_studija();
        this.espb = request.getEspb();
        this.semestar = request.getSemestar();
        this.fond_predavanja = request.getFond_predavanja();
        this.fond_vezbe = request.getFond_vezbe();
        this.fond_praktikum = request.getFond_praktikum();
    }

    public Predmet() {
        //default vrednosti
        this.fond_predavanja = 0;
        this.fond_vezbe = 0;
        this.fond_praktikum = 0;
    }

    @Override
    public String toString() {
        return "Predmet{" +
                "\nnaziv='" + naziv + '\'' +
                ",\n tip_studija='" + tip_studija + '\'' +
                ",\n espb=" + espb +
                ",\n semestar=" + semestar +
                ",\n fond_predavanja=" + fond_predavanja +
                ",\n fond_vezbe=" + fond_vezbe +
                ",\n fond_praktikum=" + fond_praktikum +
                '}';
    }
}
