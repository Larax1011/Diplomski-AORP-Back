package raf.lazar.diplomski_aorp.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.lazar.diplomski_aorp.model.*;
import raf.lazar.diplomski_aorp.model.dto.NerasporedjenPredmetDTO;
import raf.lazar.diplomski_aorp.repositories.PredavacRepository;
import raf.lazar.diplomski_aorp.repositories.PredmetRepository;
import raf.lazar.diplomski_aorp.repositories.SkolskaGodinaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PredmetService implements IService<Predmet, Long> {

    private PredmetRepository predmetRepository;
    private SkolskaGodinaRepository skolskaGodinaRepository;
    private PredavacRepository predavacRepository;

    @Override
    public <S extends Predmet> S save(S var1) {
        return this.predmetRepository.save(var1);
    }

    @Override
    public Optional<Predmet> findById(Long var1) {
        return this.predmetRepository.findById(var1);
    }

    @Override
    public List<Predmet> findAll() {
        return this.predmetRepository.findAll();
    }

    @Override
    public void deleteById(Long var1) {
        this.predmetRepository.deleteById(var1);
    }

    public List<NerasporedjenPredmetDTO> findAllNerasporedjeniPredmeti(Integer pocetnaGodina, Integer krajnjaGodina) {
        List<Predmet> predmeti = this.predmetRepository.findAll();
        List<NerasporedjenPredmetDTO> nerasporedjeni = new ArrayList<>();
        SkolskaGodina skolskaGodina = this.skolskaGodinaRepository.findSkolskaGodinaByPocetnaGodinaAndKrajnjaGodina(pocetnaGodina, krajnjaGodina);

        for (Predmet predmet : predmeti) {
            Termini termini = null;
            //trazimo termine za trazenu godinu
            for (Termini t : predmet.getTermini()) {
                if (t.getSkolskaGodina().equals(skolskaGodina)) {
                    termini = t;
                    break;
                }
            }
            if (termini != null) {
                int brPredavanja = 0;
                int brVezbe = 0;
                int brPraktikum = 0;

                for (Predavanje p : predmet.getPredavanja()) {
                    if (p.getTip().equalsIgnoreCase("predavanje")) {
                        brPredavanja += p.getBr_termina();
                    }
                    if (p.getTip().equalsIgnoreCase("vezbe")) {
                        brVezbe += p.getBr_termina();
                    }
                    if (p.getTip().equalsIgnoreCase("praktikum")) {
                        brPraktikum += p.getBr_termina();
                    }
                }

                if (brPredavanja != termini.getBr_termina_predavanja().intValue()) {
                    NerasporedjenPredmetDTO np = new NerasporedjenPredmetDTO(predmet, termini, brPredavanja, brVezbe, brPraktikum);
                    nerasporedjeni.add(np);
                    continue;
                }
                if (brVezbe != termini.getBr_termina_vezbe().intValue()) {
                    NerasporedjenPredmetDTO np = new NerasporedjenPredmetDTO(predmet, termini, brPredavanja, brVezbe, brPraktikum);
                    nerasporedjeni.add(np);
                    continue;
                }
                if (brPraktikum != termini.getBr_termina_praktikum().intValue()) {
                    NerasporedjenPredmetDTO np = new NerasporedjenPredmetDTO(predmet, termini, brPredavanja, brVezbe, brPraktikum);
                    nerasporedjeni.add(np);
                    continue;
                }


            }
        }

        return nerasporedjeni;
    }

    public void delete(Predmet predmet) {
        //predavanja
        for (Predavanje predavanje : predmet.getPredavanja()) {
            SkolskaGodina skolskaGodina = this.skolskaGodinaRepository.findById(predavanje.getSkolskaGodina().getId()).get();
            Predavac predavac = this.predavacRepository.findById(predavanje.getPredavac().getId()).get();
            skolskaGodina.getPredavanja().remove(predavanje);
            predavac.getPredavanja().remove(predavanje);
            this.predavacRepository.save(predavac);
            this.skolskaGodinaRepository.save(skolskaGodina);
        }
        //termini
        for (Termini termini : predmet.getTermini()) {
            SkolskaGodina skolskaGodina = this.skolskaGodinaRepository.findById(termini.getSkolskaGodina().getId()).get();
            skolskaGodina.getTermini().remove(termini);
            this.skolskaGodinaRepository.save(skolskaGodina);
        }

        deleteById(predmet.getId());
    }
}
