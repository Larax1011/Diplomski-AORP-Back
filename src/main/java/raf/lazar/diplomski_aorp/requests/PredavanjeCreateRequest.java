package raf.lazar.diplomski_aorp.requests;

import lombok.Data;
import raf.lazar.diplomski_aorp.model.Predavac;
import raf.lazar.diplomski_aorp.model.Predmet;
import raf.lazar.diplomski_aorp.model.SkolskaGodina;

@Data
public class PredavanjeCreateRequest {

    private String tip;
    private Integer br_termina;
    private Predavac predavac;
    private Predmet predmet;
    private SkolskaGodina skolskaGodina;
}
