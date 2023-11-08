package raf.lazar.diplomski_aorp.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import raf.lazar.diplomski_aorp.model.Predavac;
import raf.lazar.diplomski_aorp.model.SkolskaGodina;
import raf.lazar.diplomski_aorp.model.VanredniCas;
import raf.lazar.diplomski_aorp.repositories.PredavacRepository;
import raf.lazar.diplomski_aorp.repositories.SkolskaGodinaRepository;
import raf.lazar.diplomski_aorp.repositories.VanredniCasRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VanredniCasService implements IService<VanredniCas, Long> {

    private VanredniCasRepository vanredniCasRepository;
    private PredavacRepository predavacRepository;
    private SkolskaGodinaRepository skolskaGodinaRepository;

    @Override
    public <S extends VanredniCas> S save(S var1) {
        Predavac predavac = this.predavacRepository.findById(var1.getPredavac().getId()).get();
        SkolskaGodina skolskaGodina = this.skolskaGodinaRepository.findById(var1.getSkolskaGodina().getId()).get();

        var1 = this.vanredniCasRepository.save(var1);

        skolskaGodina.getVanredniCasovi().add(var1);
        predavac.getVanredniCasovi().add(var1);
        this.predavacRepository.save(predavac);
        this.skolskaGodinaRepository.save(skolskaGodina);


        return var1;
    }

    @Override
    public Optional<VanredniCas> findById(Long var1) {
        return this.vanredniCasRepository.findById(var1);
    }

    @Override
    public List<VanredniCas> findAll() {
        return this.vanredniCasRepository.findAll();
    }

    @Override
    public void deleteById(Long var1) {
        this.vanredniCasRepository.deleteById(var1);
    }

    public void delete(VanredniCas vanredniCas) {

        Predavac predavac = this.predavacRepository.findById(vanredniCas.getPredavac().getId()).get();
        SkolskaGodina skolskaGodina = this.skolskaGodinaRepository.findById(vanredniCas.getSkolskaGodina().getId()).get();

        skolskaGodina.getVanredniCasovi().remove(vanredniCas);
        predavac.getVanredniCasovi().remove(vanredniCas);
        this.skolskaGodinaRepository.save(skolskaGodina);
        this.predavacRepository.save(predavac);

        deleteById(vanredniCas.getId());
    }
}
