package raf.lazar.diplomski_aorp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.lazar.diplomski_aorp.model.*;
import raf.lazar.diplomski_aorp.requests.PredavacCreateRequest;
import raf.lazar.diplomski_aorp.services.PredavacService;

import java.util.List;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/predavac")
@AllArgsConstructor
public class PredavacRestController {

    private final PredavacService predavacService;


    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPredavac(@RequestBody PredavacCreateRequest request) {
        Predavac predavac = new Predavac(request);
        predavac = this.predavacService.save(predavac);
        if (predavac != null)
            return new ResponseEntity<>(predavac, HttpStatus.CREATED);
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAllPredavac() {
        List<Predavac> predavacList = this.predavacService.findAll();
        System.out.println("I AM CALLED");
        if (predavacList != null) {
            return new ResponseEntity<>(predavacList, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping()
    public ResponseEntity<?> getPredavacById(@RequestParam Long id) {
        Optional<Predavac> predavac = this.predavacService.findById(id);
        if (predavac.isPresent()) {
            return new ResponseEntity<>(predavac, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();

    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updatePredavac(@RequestBody Predavac predavac) {
        System.out.println(predavac);
        predavac = this.predavacService.save(predavac);
        if (predavac != null) {
            return new ResponseEntity<>(predavac, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deletePredavac(@RequestParam Long id) {
        Optional<Predavac> predavac = this.predavacService.findById(id);
        if (predavac.isPresent()) {
            this.predavacService.delete(predavac.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
