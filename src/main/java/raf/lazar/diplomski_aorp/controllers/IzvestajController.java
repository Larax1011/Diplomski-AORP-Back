package raf.lazar.diplomski_aorp.controllers;

import com.itextpdf.text.DocumentException;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.lazar.diplomski_aorp.services.*;

import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping("/izvestaj")
@AllArgsConstructor
public class IzvestajController {

    private final IzvestajService izvestajService;

    @GetMapping(path = "/profesori", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> getIzvestajProfesori(
            @RequestParam Boolean vanredniCasovi,
            @RequestParam Integer pocetnaGodina,
            @RequestParam Integer krajnjaGodina) throws DocumentException {

        byte[] pdf = {};
        pdf = this.izvestajService.makeIzvestaj(pocetnaGodina, krajnjaGodina, vanredniCasovi).getReport();

        if (pdf.length > 0) {
            return ResponseEntity.ok(pdf);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(path = "/predmeti", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> getIzvestajPredmeti(
            @RequestParam Integer pocetnaGodina,
            @RequestParam Integer krajnjaGodina) throws DocumentException {
        byte[] pdf = {};
        pdf = izvestajService.makePredmetIzvestaj(pocetnaGodina.intValue(), krajnjaGodina.intValue()).getReport();
        if (pdf.length > 0) {
            return ResponseEntity.ok(pdf);
        }
        return ResponseEntity.badRequest().build();
    }




}
