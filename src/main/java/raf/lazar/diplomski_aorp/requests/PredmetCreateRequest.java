package raf.lazar.diplomski_aorp.requests;

import lombok.Data;
import raf.lazar.diplomski_aorp.model.Predavanje;
import raf.lazar.diplomski_aorp.model.Termini;

import javax.persistence.*;
import java.util.List;

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
