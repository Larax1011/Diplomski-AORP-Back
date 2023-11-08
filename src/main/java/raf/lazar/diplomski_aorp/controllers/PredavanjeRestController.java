package raf.lazar.diplomski_aorp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.lazar.diplomski_aorp.model.Predavac;
import raf.lazar.diplomski_aorp.model.Predavanje;
import raf.lazar.diplomski_aorp.model.Predmet;
import raf.lazar.diplomski_aorp.model.SkolskaGodina;
import raf.lazar.diplomski_aorp.requests.PredavanjeCreateRequest;
import raf.lazar.diplomski_aorp.services.PredavacService;
import raf.lazar.diplomski_aorp.services.PredavanjeService;
import raf.lazar.diplomski_aorp.services.PredmetService;
import raf.lazar.diplomski_aorp.services.SkolskaGodinaService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/predavanje")
@AllArgsConstructor
public class PredavanjeRestController {

    private final PredavanjeService predavanjeService;


    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPredavanje(@RequestBody PredavanjeCreateRequest request) {

        Predavanje predavanje = new Predavanje(request);
        predavanje = this.predavanjeService.save(predavanje);

        if (predavanje != null)
            return new ResponseEntity<>(predavanje, HttpStatus.CREATED);
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAllPredavanje() {
        List<Predavanje> predavanjeList = this.predavanjeService.findAll();
        if (predavanjeList != null) {
            return new ResponseEntity<>(predavanjeList, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping()
    public ResponseEntity<?> getPredavanjeById(@RequestParam Long id) {
        Optional<Predavanje> predavanje = this.predavanjeService.findById(id);
        if (predavanje.isPresent()) {
            return new ResponseEntity<>(predavanje, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();

    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updatePredavanje(@RequestBody Predavanje predavanje) {
        predavanje = this.predavanjeService.save(predavanje);
        if (predavanje != null) {
            return new ResponseEntity<>(predavanje, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deletePredavanje(@RequestParam Long id) {
        Optional<Predavanje> predavanje = this.predavanjeService.findById(id);
        if (predavanje.isPresent()) {
            this.predavanjeService.delete(predavanje.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
