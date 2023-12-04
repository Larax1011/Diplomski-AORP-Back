package raf.lazar.diplomski_aorp.requests;

import lombok.Data;

@Data
public class PredmetCreateRequest {

    private String naziv;
    private String tip_studija;
    private Integer espb;
    private Integer semestar;
    private Integer fond_predavanja;
    private Integer fond_vezbe;
    private Integer fond_praktikum;
}
