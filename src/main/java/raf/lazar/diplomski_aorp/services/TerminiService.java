package raf.lazar.diplomski_aorp.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.lazar.diplomski_aorp.model.Predmet;
import raf.lazar.diplomski_aorp.model.SkolskaGodina;
import raf.lazar.diplomski_aorp.model.Termini;
import raf.lazar.diplomski_aorp.repositories.PredmetRepository;
import raf.lazar.diplomski_aorp.repositories.SkolskaGodinaRepository;
import raf.lazar.diplomski_aorp.repositories.TerminiRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TerminiService implements IService<Termini, Long> {

    private TerminiRepository terminiRepository;
    private SkolskaGodinaRepository skolskaGodinaRepository;
    private PredmetRepository predmetRepository;

    @Override
    public <S extends Termini> S save(S var1) {
        Predmet predmet = this.predmetRepository.findById(var1.getPredmet().getId()).get();
        SkolskaGodina skolskaGodina = this.skolskaGodinaRepository.findById(var1.getSkolskaGodina().getId()).get();


        var1 = this.terminiRepository.save(var1);

        predmet.getTermini().add(var1);
        skolskaGodina.getTermini().add(var1);

        this.predmetRepository.save(predmet);
        this.skolskaGodinaRepository.save(skolskaGodina);


        return var1;
    }

    @Override
    public Optional<Termini> findById(Long var1) {
        return this.terminiRepository.findById(var1);
    }

    @Override
    public List<Termini> findAll() {
        return this.terminiRepository.findAll();
    }

    @Override
    public void deleteById(Long var1) {
        this.terminiRepository.deleteById(var1);
    }

    public void delete(Termini termini) {
        Predmet predmet = this.predmetRepository.findById(termini.getPredmet().getId()).get();
        SkolskaGodina skolskaGodina = this.skolskaGodinaRepository.findById(termini.getSkolskaGodina().getId()).get();

        skolskaGodina.getTermini().remove(termini);
        predmet.getTermini().remove(termini);

        this.skolskaGodinaRepository.save(skolskaGodina);
        this.predmetRepository.save(predmet);

        deleteById(termini.getId());
    }
}
