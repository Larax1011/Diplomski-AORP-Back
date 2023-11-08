package raf.lazar.diplomski_aorp.requests;

import lombok.Data;
import raf.lazar.diplomski_aorp.model.Predavac;
import raf.lazar.diplomski_aorp.model.SkolskaGodina;

@Data
public class VanredniCasCreateRequest {
    private String opis;
    private Integer br_casova;
    private Predavac predavac;
    private SkolskaGodina skolskaGodina;

}
