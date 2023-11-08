package raf.lazar.diplomski_aorp.requests;

import lombok.Data;
import raf.lazar.diplomski_aorp.model.Predmet;
import raf.lazar.diplomski_aorp.model.SkolskaGodina;


@Data
public class TerminiCreateRequest {

    private Integer brTerminaPredavanja;
    private Integer brTerminaVezbe;
    private Integer brTerminaPraktikum;
    private Predmet predmet;
        private SkolskaGodina skolskaGodina;

}
