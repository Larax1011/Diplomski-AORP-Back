package raf.lazar.diplomski_aorp.requests;

import lombok.Data;

@Data
public class SkolskaGodinaCreateRequest {

    private Integer pocetnaGodina;
    private Integer krajnjaGodina;
    private Integer brNedeljaUSemestru;

}
