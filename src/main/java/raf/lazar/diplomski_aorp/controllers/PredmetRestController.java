package raf.lazar.diplomski_aorp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.lazar.diplomski_aorp.model.*;
import raf.lazar.diplomski_aorp.model.dto.NerasporedjenPredmetDTO;
import raf.lazar.diplomski_aorp.requests.PredmetCreateRequest;
import raf.lazar.diplomski_aorp.services.PredmetService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/predmet")
@AllArgsConstructor
public class PredmetRestController {

    private final PredmetService predmetService;


    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPredmet(@RequestBody PredmetCreateRequest request) {
        Predmet predmet = new Predmet(request);
        predmet = this.predmetService.save(predmet);
        if (predmet != null)
            return new ResponseEntity<>(predmet, HttpStatus.CREATED);
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAllPredmet() {
        List<Predmet> predmetList = this.predmetService.findAll();
        if (predmetList != null) {
            return new ResponseEntity<>(predmetList, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping()
    public ResponseEntity<?> getPredmetById(@RequestParam Long id) {
        Optional<Predmet> predmet = this.predmetService.findById(id);
        if (predmet.isPresent()) {
            return new ResponseEntity<>(predmet, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();

    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updatePredmet(@RequestBody Predmet predmet) {
        predmet = this.predmetService.save(predmet);
        if (predmet != null) {
            return new ResponseEntity<>(predmet, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deletePredmet(@RequestParam Long id) {
        Optional<Predmet> predmet = this.predmetService.findById(id);
        if (predmet.isPresent()) {
            this.predmetService.delete(predmet.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/getAllNerasporedjeniPredmeti")
    public ResponseEntity<?> getAllNerasporedjeniPredmeti(@RequestParam Integer pocetnaGodina, @RequestParam Integer krajnjaGodina) {
        List<NerasporedjenPredmetDTO> nerasporedjeniPredmeti = this.predmetService.findAllNerasporedjeniPredmeti(pocetnaGodina, krajnjaGodina);

        if (nerasporedjeniPredmeti != null) {
            return new ResponseEntity<>(nerasporedjeniPredmeti, HttpStatus.OK);
        }

        return ResponseEntity.badRequest().build();

    }
}
