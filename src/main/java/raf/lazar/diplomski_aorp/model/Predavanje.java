package raf.lazar.diplomski_aorp.model;


import javax.persistence.*;

import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import raf.lazar.diplomski_aorp.requests.PredavanjeCreateRequest;

@Entity
@Data
public class Predavanje {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String tip;
    @Column(nullable = false)
    private Integer br_termina;
    @ManyToOne
    private Predavac predavac;
    @ManyToOne
    private Predmet predmet;
    @ManyToOne
    private SkolskaGodina skolskaGodina;


    public Predavanje(PredavanjeCreateRequest request) {
        this.tip = request.getTip();
        this.br_termina = request.getBr_termina();
        this.predavac = request.getPredavac();
        this.predmet = request.getPredmet();
        this.skolskaGodina = request.getSkolskaGodina();
    }

    public Predavanje() {
    }
}
