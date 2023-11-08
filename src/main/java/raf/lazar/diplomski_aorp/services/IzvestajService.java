package raf.lazar.diplomski_aorp.services;

import lombok.AllArgsConstructor;
import org.hibernate.event.spi.SaveOrUpdateEvent;
import org.springframework.stereotype.Service;
import raf.lazar.diplomski_aorp.model.Predavac;
import raf.lazar.diplomski_aorp.model.Predavanje;
import raf.lazar.diplomski_aorp.model.Predmet;
import raf.lazar.diplomski_aorp.model.SkolskaGodina;
import raf.lazar.diplomski_aorp.model.izvestaj.IzvestajTableContent;
import raf.lazar.diplomski_aorp.model.izvestaj.Reports;
import raf.lazar.diplomski_aorp.model.izvestaj.TableReport;
import raf.lazar.diplomski_aorp.repositories.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Service
@AllArgsConstructor
public class IzvestajService{

    private PredavacRepository predavacRepository;
    private PredmetRepository predmetRepository;
    private SkolskaGodinaRepository skolskaGodinaRepository;


    public Reports makeFullIzvestaj(int pocetnaGodina, int krajnjaGodina) {

        List<Predavac> predavacList = this.predavacRepository.findAll();
        SkolskaGodina skolskaGodina = this.skolskaGodinaRepository.findSkolskaGodinaByPocetnaGodinaAndKrajnjaGodina(pocetnaGodina, krajnjaGodina);

        IzvestajTableContent itc = new IzvestajTableContent();

        //ako nema predavanja ni vanredne casove, ne ukljucujemo ga u izvestaj
        for (Iterator<Predavac> it = predavacList.iterator(); it.hasNext(); ) {
            Predavac predavac = it.next();
            if (predavac.getPredavanja().isEmpty() && predavac.getVanredniCasovi().isEmpty()) {
                it.remove();
            }
        }
        itc.makeFullTableContent(predavacList, skolskaGodina);

        String title = "Obracun casova " + pocetnaGodina + "/" + krajnjaGodina;
        System.out.println(itc.getColumns());
        System.out.println(itc.getRows());
        float[] columnWidths = new float[]{30f, 30f, 25f, 10f, 10f, 20f, 15f};
        return new TableReport(title, itc.getColumns(), itc.getRows(), columnWidths);
    }

    public Reports makeOfficialIzvestaj(int pocetnaGodina, int krajnjaGodina) {

        List<Predavac> predavacList = this.predavacRepository.findAll();
        SkolskaGodina skolskaGodina = this.skolskaGodinaRepository.findSkolskaGodinaByPocetnaGodinaAndKrajnjaGodina(pocetnaGodina, krajnjaGodina);

        IzvestajTableContent itc = new IzvestajTableContent();
        //ako nema predavanja ne ukljucujemo ga u izvestaj
        for (Iterator<Predavac> it = predavacList.iterator(); it.hasNext(); ) {
            Predavac predavac = it.next();
            if (predavac.getPredavanja().isEmpty()) {
                it.remove();
            }
        }
        itc.makeOfficialTableContent(predavacList, skolskaGodina);

        String title = "Obracun casova " + pocetnaGodina + "/" + krajnjaGodina;
        float[] columnWidths = new float[]{30f, 30f, 25f, 10f, 10f, 20f, 15f};
        return new TableReport(title, itc.getColumns(), itc.getRows(), columnWidths);
    }

    public Reports makePredmetIzvestaj(int pocetnaGodina, int krajnjaGodina) {
        List<Predmet> predmetList = this.predmetRepository.findAll();
        SkolskaGodina skolskaGodina = this.skolskaGodinaRepository.findSkolskaGodinaByPocetnaGodinaAndKrajnjaGodina(pocetnaGodina, krajnjaGodina);

        IzvestajTableContent itc = new IzvestajTableContent();
        for (Iterator<Predmet> it = predmetList.iterator(); it.hasNext(); ) {
            Predmet predmet = it.next();
            if (predmet.getPredavanja().isEmpty()) {
                it.remove();
            }
        }
        itc.makePredmetTableContent(predmetList, skolskaGodina);

        String title = "Izvestaj predmeta " + pocetnaGodina + "/" + krajnjaGodina;
        float[] columnWidths = new float[]{30f, 30f, 25f, 10f, 10f, 10f};
        return new TableReport(title, itc.getColumns(), itc.getRows(), columnWidths);
    }
}
