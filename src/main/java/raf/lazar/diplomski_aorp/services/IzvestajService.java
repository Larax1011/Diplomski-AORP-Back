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


    public Reports makeIzvestaj(int pocetnaGodina, int krajnjaGodina, boolean vanredni) {
        List<Predavac> predavacList = vanredni ?
                this.predavacRepository.findAllByPredavanjaIsNotEmptyOrVanredniCasoviIsNotEmpty()
                : this.predavacRepository.findAllByPredavanjaIsNotEmpty();

        SkolskaGodina skolskaGodina = this.skolskaGodinaRepository.findSkolskaGodinaByPocetnaGodinaAndKrajnjaGodina(pocetnaGodina, krajnjaGodina);

        for (Predavac predavac : predavacList) {

        }


        return vanredni ? makeFullIzvestaj(predavacList, skolskaGodina) : makeOfficialIzvestaj(predavacList, skolskaGodina);
    }
    private Reports makeFullIzvestaj(List<Predavac> predavacList, SkolskaGodina skolskaGodina) {

        IzvestajTableContent itc = new IzvestajTableContent();
        itc.makeFullTableContent(predavacList, skolskaGodina);

        String title = "Obracun casova " + skolskaGodina.getPocetnaGodina() + "/" + skolskaGodina.getKrajnjaGodina();
        System.out.println(itc.getColumns());
        System.out.println(itc.getRows());
        float[] columnWidths = new float[]{30f, 30f, 25f, 10f, 10f, 20f, 15f};
        return new TableReport(title, itc.getColumns(), itc.getRows(), columnWidths);
    }

    private Reports makeOfficialIzvestaj(List<Predavac> predavacList, SkolskaGodina skolskaGodina) {

        IzvestajTableContent itc = new IzvestajTableContent();
        itc.makeOfficialTableContent(predavacList, skolskaGodina);

        String title = "Obracun casova " + skolskaGodina.getPocetnaGodina() + "/" + skolskaGodina.getKrajnjaGodina();
        float[] columnWidths = new float[]{30f, 30f, 25f, 10f, 10f, 20f, 15f};
        return new TableReport(title, itc.getColumns(), itc.getRows(), columnWidths);
    }

    public Reports makePredmetIzvestaj(int pocetnaGodina, int krajnjaGodina) {
        List<Predmet> predmetList = this.predmetRepository.findAllByPredavanjaIsNotEmpty();
        SkolskaGodina skolskaGodina = this.skolskaGodinaRepository.findSkolskaGodinaByPocetnaGodinaAndKrajnjaGodina(pocetnaGodina, krajnjaGodina);

        IzvestajTableContent itc = new IzvestajTableContent();
        itc.makePredmetTableContent(predmetList, skolskaGodina);

        String title = "Izvestaj predmeta " + pocetnaGodina + "/" + krajnjaGodina;
        float[] columnWidths = new float[]{30f, 30f, 25f, 10f, 10f, 10f};
        return new TableReport(title, itc.getColumns(), itc.getRows(), columnWidths);
    }
}
