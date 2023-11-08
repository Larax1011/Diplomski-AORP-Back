package raf.lazar.diplomski_aorp.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import raf.lazar.diplomski_aorp.requests.SkolskaGodinaCreateRequest;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class SkolskaGodina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private Integer pocetnaGodina;
    @Column(nullable = false, unique = true)
    private Integer krajnjaGodina;
    @Column(nullable = false)
    private Integer brNedeljaUSemestru;
    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Predavanje> predavanja = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Termini> termini = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private List<VanredniCas> vanredniCasovi = new ArrayList<>();

    public SkolskaGodina(SkolskaGodinaCreateRequest request) {
        this.pocetnaGodina = request.getPocetnaGodina();
        this.krajnjaGodina = request.getKrajnjaGodina();
        this.brNedeljaUSemestru = request.getBrNedeljaUSemestru();
    }

    public SkolskaGodina() {
    }
}
