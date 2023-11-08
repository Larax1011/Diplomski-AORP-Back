package raf.lazar.diplomski_aorp.requests;

import lombok.Data;


@Data
public class PredavacCreateRequest {

    private String name;
    private String lastname;
    private String email;
    private String type;

}
