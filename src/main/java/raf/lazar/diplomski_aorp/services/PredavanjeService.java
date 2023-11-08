package raf.lazar.diplomski_aorp.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.lazar.diplomski_aorp.model.Predavac;
import raf.lazar.diplomski_aorp.model.Predavanje;
import raf.lazar.diplomski_aorp.model.Predmet;
import raf.lazar.diplomski_aorp.model.SkolskaGodina;
import raf.lazar.diplomski_aorp.repositories.PredavacRepository;
import raf.lazar.diplomski_aorp.repositories.PredavanjeRepository;
import raf.lazar.diplomski_aorp.repositories.PredmetRepository;
import raf.lazar.diplomski_aorp.repositories.SkolskaGodinaRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PredavanjeService implements IService<Predavanje, Long> {

    private PredavanjeRepository predavanjeRepository;
    private PredavacRepository predavacRepository;
    private PredmetRepository predmetRepository;
    private SkolskaGodinaRepository skolskaGodinaRepository;


    @Override
    public <S extends Predavanje> S save(S var1) {
        Predavac predavac = this.predavacRepository.findById(var1.getPredavac().getId()).get();
        Predmet predmet = this.predmetRepository.findById(var1.getPredmet().getId()).get();
        SkolskaGodina skolskaGodina = this.skolskaGodinaRepository.findById(var1.getSkolskaGodina().getId()).get();

        var1 = this.predavanjeRepository.save(var1);

        predavac.getPredavanja().add(var1);
        predmet.getPredavanja().add(var1);
        skolskaGodina.getPredavanja().add(var1);
        this.predavacRepository.save(predavac);
        this.predmetRepository.save(predmet);
        this.skolskaGodinaRepository.save(skolskaGodina);

        return var1;
    }

    @Override
    public Optional<Predavanje> findById(Long var1) {
        return this.predavanjeRepository.findById(var1);
    }

    @Override
    public List<Predavanje> findAll() {
        return this.predavanjeRepository.findAll();
    }

    @Override
    public void deleteById(Long var1) {
        this.predavanjeRepository.deleteById(var1);
    }

    public void delete(Predavanje predavanje) {
        Predavac predavac = this.predavacRepository.findById(predavanje.getPredavac().getId()).get();
        Predmet predmet = this.predmetRepository.findById(predavanje.getPredmet().getId()).get();
        SkolskaGodina skolskaGodina = this.skolskaGodinaRepository.findById(predavanje.getSkolskaGodina().getId()).get();

        predavac.getPredavanja().remove(predavanje);
        skolskaGodina.getPredavanja().remove(predavanje);
        predmet.getPredavanja().remove(predavanje);

        this.predavacRepository.save(predavac);
        this.predmetRepository.save(predmet);
        this.skolskaGodinaRepository.save(skolskaGodina);

        deleteById(predavanje.getId());
    }
}
