package raf.lazar.diplomski_aorp.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raf.lazar.diplomski_aorp.model.*;
import raf.lazar.diplomski_aorp.requests.SkolskaGodinaCreateRequest;
import raf.lazar.diplomski_aorp.services.SkolskaGodinaService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/skolska_godina")
@AllArgsConstructor
public class SkolskaGodinaRestController {

    private final SkolskaGodinaService SkolskaGodinaService;


    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createSkolskaGodina(@RequestBody SkolskaGodinaCreateRequest request) {
        SkolskaGodina skolskaGodina = new SkolskaGodina(request);
        skolskaGodina = this.SkolskaGodinaService.save(skolskaGodina);
        if (skolskaGodina != null)
            return new ResponseEntity<>(skolskaGodina, HttpStatus.CREATED);
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/getAll")
    public ResponseEntity<?> getAllSkolskaGodina() {
        List<SkolskaGodina> skolskaGodinaList = this.SkolskaGodinaService.findAll();
        if (skolskaGodinaList != null) {
            return new ResponseEntity<>(skolskaGodinaList, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping()
    public ResponseEntity<?> getSkolskaGodinaById(@RequestParam Long id) {
        Optional<SkolskaGodina> skolskaGodina = this.SkolskaGodinaService.findById(id);
        if (skolskaGodina.isPresent()) {
            return new ResponseEntity<>(skolskaGodina, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();

    }

    @PutMapping(value = "/update")
    public ResponseEntity<?> updateSkolskaGodina(@RequestBody SkolskaGodina skolskaGodina) {
        skolskaGodina = this.SkolskaGodinaService.save(skolskaGodina);
        if (skolskaGodina != null) {
            return new ResponseEntity<>(skolskaGodina, HttpStatus.OK);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteSkolskaGodina(@RequestParam Long id) {
        Optional<SkolskaGodina> skolskaGodina = this.SkolskaGodinaService.findById(id);
        if (skolskaGodina.isPresent()) {
            this.SkolskaGodinaService.delete(skolskaGodina.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }



}
