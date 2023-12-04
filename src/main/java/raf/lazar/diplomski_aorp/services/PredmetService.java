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
        System.out.println(pocetnaGodina);
        System.out.println(krajnjaGodina);
        SkolskaGodina skolskaGodina = this.skolskaGodinaRepository.findSkolskaGodinaByPocetnaGodinaAndKrajnjaGodina(pocetnaGodina, krajnjaGodina);

        for (Predmet predmet : predmeti) {
            Termini termini = null;
            //trazimo termine za trazenu godinu
            for (Termini t : predmet.getTermini()) {
                System.out.println("GODINA IS " + t.getSkolskaGodina().getPocetnaGodina());
                if (t.getSkolskaGodina().equals(skolskaGodina)) {
                    termini = t;
                    break;
                }
            }
            if (termini != null) {
                System.out.println("TERMINI NOT NULL");
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

                if (brPredavanja != termini.getBr_termina_predavanja()) {
                    NerasporedjenPredmetDTO np = new NerasporedjenPredmetDTO(predmet, termini, brPredavanja, brVezbe, brPraktikum);
                    nerasporedjeni.add(np);
                    System.out.println("DO THIS1");
                    continue;
                }
                if (brVezbe != termini.getBr_termina_vezbe()) {
                    NerasporedjenPredmetDTO np = new NerasporedjenPredmetDTO(predmet, termini, brPredavanja, brVezbe, brPraktikum);
                    nerasporedjeni.add(np);
                    System.out.println("DO THIS2");
                    continue;
                }
                if (brPraktikum != termini.getBr_termina_praktikum()) {
                    NerasporedjenPredmetDTO np = new NerasporedjenPredmetDTO(predmet, termini, brPredavanja, brVezbe, brPraktikum);
                    nerasporedjeni.add(np);
                    System.out.println("DO THIS3");
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
