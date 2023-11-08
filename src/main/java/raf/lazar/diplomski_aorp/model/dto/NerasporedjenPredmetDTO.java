package raf.lazar.diplomski_aorp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import raf.lazar.diplomski_aorp.model.Predmet;
import raf.lazar.diplomski_aorp.model.Termini;

@AllArgsConstructor
@Data
public class NerasporedjenPredmetDTO {

    private Predmet predmet;
    private Termini termini;
    private int brRasporedjenihPredavanja;
    private int brRasporedjenihVezbi;
    private int brRasporedjenihPraktikuma;


}
