package raf.lazar.diplomski_aorp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.lazar.diplomski_aorp.model.VanredniCas;
import raf.lazar.diplomski_aorp.requests.VanredniCasCreateRequest;
import raf.lazar.diplomski_aorp.services.VanredniCasService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/vanredni_cas")
@AllArgsConstructor
public class VanredniCasRestController {

    private final VanredniCasService VanredniCasService;

    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createVanredniCas(@RequestBody VanredniCasCreateRequest request) {
        VanredniCas vanredniCas = new VanredniCas(request);
        vanredniCas = this.VanredniCasService.save(vanredniCas);
        if (vanredniCas != null)
            return new ResponseEntity<>(vanredniCas, HttpStatus.CREATED);
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAllVanredniCas() {
        List<VanredniCas> vanredniCasList = this.VanredniCasService.findAll();
        if (vanredniCasList != null) {
            return new ResponseEntity<>(vanredniCasList, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping()
    public ResponseEntity<?> getVanredniCasById(@RequestParam Long id) {
        Optional<VanredniCas> vanredniCas = this.VanredniCasService.findById(id);
        if (vanredniCas.isPresent()) {
            return new ResponseEntity<>(vanredniCas, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();

    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateVanredniCas(@RequestBody VanredniCas vanredniCas) {
        vanredniCas = this.VanredniCasService.save(vanredniCas);
        if (vanredniCas != null) {
            return new ResponseEntity<>(vanredniCas, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteVanredniCas(@RequestParam Long id) {
        Optional<VanredniCas> vanredniCas = this.VanredniCasService.findById(id);
        if (vanredniCas.isPresent()) {
            this.VanredniCasService.delete(vanredniCas.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

}
