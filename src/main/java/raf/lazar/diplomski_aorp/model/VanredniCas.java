package raf.lazar.diplomski_aorp.model;


import javax.persistence.*;
import lombok.Data;
import raf.lazar.diplomski_aorp.requests.VanredniCasCreateRequest;

@Entity
@Data
public class VanredniCas {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String opis;
    @Column(nullable = false)
    private Integer br_casova;
    @ManyToOne
    private Predavac predavac;
    @ManyToOne
    private SkolskaGodina skolskaGodina;

    public VanredniCas(VanredniCasCreateRequest request) {
        this.opis = request.getOpis();
        this.br_casova = request.getBr_casova();
        this.predavac = request.getPredavac();
        this.skolskaGodina = request.getSkolskaGodina();
    }

    public VanredniCas() {

    }
}
