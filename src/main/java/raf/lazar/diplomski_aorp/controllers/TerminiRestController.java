package raf.lazar.diplomski_aorp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.lazar.diplomski_aorp.model.Predmet;
import raf.lazar.diplomski_aorp.model.SkolskaGodina;
import raf.lazar.diplomski_aorp.model.Termini;
import raf.lazar.diplomski_aorp.requests.TerminiCreateRequest;
import raf.lazar.diplomski_aorp.services.PredmetService;
import raf.lazar.diplomski_aorp.services.SkolskaGodinaService;
import raf.lazar.diplomski_aorp.services.TerminiService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/termini")
@AllArgsConstructor
public class TerminiRestController {

    private final TerminiService terminiService;


    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createTermini(@RequestBody TerminiCreateRequest request) {
        Termini termini = new Termini(request);

        termini = this.terminiService.save(termini);

        if (termini != null)
            return new ResponseEntity<>(termini, HttpStatus.CREATED);
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAllTermini() {
        List<Termini> terminiList = this.terminiService.findAll();
        if (terminiList != null) {
            return new ResponseEntity<>(terminiList, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping()
    public ResponseEntity<?> getTerminiById(@RequestParam Long id) {
        Optional<Termini> termini = this.terminiService.findById(id);
        if (termini.isPresent()) {
            return new ResponseEntity<>(termini, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();

    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateTermini(@RequestBody Termini termini) {
        termini = this.terminiService.save(termini);
        if (termini != null) {
            return new ResponseEntity<>(termini, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteTermini(@RequestParam Long id) {
        Optional<Termini> termini = this.terminiService.findById(id);
        if (termini.isPresent()) {
            this.terminiService.delete(termini.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }





}



