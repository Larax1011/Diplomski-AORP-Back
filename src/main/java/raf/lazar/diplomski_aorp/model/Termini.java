package raf.lazar.diplomski_aorp.model;

import javax.persistence.*;
import lombok.Data;
import raf.lazar.diplomski_aorp.requests.TerminiCreateRequest;

@Entity
@Data
public class Termini {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //br termina oznacava koliko je potrebno termina da bi se rasporedile sve grupe za dati tip predavanja
    @Column(nullable = false)
    private Integer br_termina_predavanja;
    @Column(nullable = false)
    private Integer br_termina_vezbe;
    @Column(nullable = false)
    private Integer br_termina_praktikum;
    @ManyToOne
    private Predmet predmet;
    @ManyToOne
    private SkolskaGodina skolskaGodina;

    public Termini(TerminiCreateRequest request) {
        this.br_termina_predavanja = request.getBrTerminaPredavanja();
        this.br_termina_vezbe = request.getBrTerminaVezbe();
        this.br_termina_praktikum = request.getBrTerminaPraktikum();
        this.predmet = request.getPredmet();
        this.skolskaGodina = request.getSkolskaGodina();
    }

    public Termini() {

    }
}
