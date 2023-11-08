package raf.lazar.diplomski_aorp.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.lazar.diplomski_aorp.model.*;
import raf.lazar.diplomski_aorp.repositories.PredavacRepository;
import raf.lazar.diplomski_aorp.repositories.PredmetRepository;
import raf.lazar.diplomski_aorp.repositories.SkolskaGodinaRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PredavacService implements IService<Predavac, Long> {


    private PredavacRepository predavacRepository;
    private SkolskaGodinaRepository skolskaGodinaRepository;
    private PredmetRepository predmetRepository;

    @Override
    public <S extends Predavac> S save(S var1) {
        return this.predavacRepository.save(var1);
    }

    @Override
    public Optional<Predavac> findById(Long var1) {
        return this.predavacRepository.findById(var1);
    }

    @Override
    public List<Predavac> findAll() {
        return this.predavacRepository.findAll();
    }

    @Override
    public void deleteById(Long var1) {
        this.predavacRepository.deleteById(var1);
    }

    public void delete(Predavac predavac) {
        //predavanja
        for (Predavanje predavanje : predavac.getPredavanja()) {
            SkolskaGodina skolskaGodina = this.skolskaGodinaRepository.findById(predavanje.getSkolskaGodina().getId()).get();
            Predmet predmet = this.predmetRepository.findById(predavanje.getPredmet().getId()).get();
            skolskaGodina.getPredavanja().remove(predavanje);
            predmet.getPredavanja().remove(predavanje);
            this.predmetRepository.save(predmet);
            this.skolskaGodinaRepository.save(skolskaGodina);
        }
        //vanredni casovi
        for (VanredniCas vanredniCas : predavac.getVanredniCasovi()) {
            SkolskaGodina skolskaGodina = this.skolskaGodinaRepository.findById(vanredniCas.getSkolskaGodina().getId()).get();
            skolskaGodina.getVanredniCasovi().remove(vanredniCas);
            this.skolskaGodinaRepository.save(skolskaGodina);
        }
        deleteById(predavac.getId());
    }
}
