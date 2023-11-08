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
public class SkolskaGodinaService implements IService<SkolskaGodina,Long>{

    private SkolskaGodinaRepository skolskaGodinaRepository;
    private PredmetRepository predmetRepository;
    private PredavacRepository predavacRepository;

    @Override
    public <S extends SkolskaGodina> S save(S var1) {
        return this.skolskaGodinaRepository.save(var1);
    }

    @Override
    public Optional<SkolskaGodina> findById(Long var1) {
        return this.skolskaGodinaRepository.findById(var1);
    }

    @Override
    public List<SkolskaGodina> findAll() {
        return this.skolskaGodinaRepository.findAll();
    }

    @Override
    public void deleteById(Long var1) {
        this.skolskaGodinaRepository.deleteById(var1);
    }

    public void delete(SkolskaGodina skolskaGodina) {
        //predavanja
        for (Predavanje predavanje : skolskaGodina.getPredavanja()) {
            Predmet predmet = this.predmetRepository.findById(predavanje.getPredmet().getId()).get();
            Predavac predavac = this.predavacRepository.findById(predavanje.getPredavac().getId()).get();
            predmet.getPredavanja().remove(predavanje);
            predavac.getPredavanja().remove(predavanje);
            this.predavacRepository.save(predavac);
            this.predmetRepository.save(predmet);
        }
        //termini
        for (Termini termini : skolskaGodina.getTermini()) {
            Predmet predmet = this.predmetRepository.findById(termini.getPredmet().getId()).get();
            predmet.getTermini().remove(termini);
            this.predmetRepository.save(predmet);
        }
        for (VanredniCas vanredniCas : skolskaGodina.getVanredniCasovi()) {
            Predavac predavac = this.predavacRepository.getById(vanredniCas.getPredavac().getId());
            predavac.getVanredniCasovi().remove(vanredniCas);
            this.predavacRepository.save(predavac);
        }
        deleteById(skolskaGodina.getId());
    }
}
