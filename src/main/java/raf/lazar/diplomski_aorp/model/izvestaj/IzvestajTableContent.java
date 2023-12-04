package raf.lazar.diplomski_aorp.model.izvestaj;

import lombok.Data;
import raf.lazar.diplomski_aorp.model.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data
public class IzvestajTableContent {

    private List<String> columns;
    private List<List<String>> rows;

    public IzvestajTableContent() {
        columns = new ArrayList<>();
        rows = new ArrayList<>();
    }

    public void makeOfficialTableContent(List<Predavac> predavacList, SkolskaGodina skolskaGodina) {
        setProfesorColumns();

        boolean firstRow;
        for (Predavac predavac : predavacList) {
            double ukupnoProf = 0.0;
            double ukupnoParni = 0.0;
            double ukupnoNeparni = 0.0;
            double ukupnoGodina = 0.0;
            firstRow = true;

            //ako nisu iste skolske godine
            predavac.getPredavanja().removeIf(predavanje -> predavanje.getSkolskaGodina().getPocetnaGodina().intValue() != skolskaGodina.getPocetnaGodina().intValue());
            if (predavac.getPredavanja().isEmpty()) {
                continue;
            }


            List<String> nedeljaRow = new ArrayList<>();
            List<String> semestarRow = new ArrayList<>();
            List<String> godinaRow = new ArrayList<>();

            for (Predavanje predavanje : predavac.getPredavanja()) {
                List<String> row = new ArrayList<>();
                double ukupnoPredmet = 0.0;
                //ime prof
                row.add(firstRow ? predavac.getName() + " " + predavac.getLastname() : "");
                firstRow = false;
                //ime predmeta
                row.add(predavanje.getPredmet().getNaziv());
                //tip predavanja
                row.add(predavanje.getTip());
                //semestar
                row.add(predavanje.getPredmet().getSemestar().toString());
                //br termina
                row.add(predavanje.getBr_termina().toString());
                //racunati casovi
                if (predavac.getType().equalsIgnoreCase("profesor")) {
                    if (predavanje.getTip().equalsIgnoreCase("vezbe")) {
                        row.add(predavanje.getPredmet().getFond_vezbe().toString());
                        ukupnoPredmet += predavanje.getPredmet().getFond_vezbe() * predavanje.getBr_termina();
                    }
                    if (predavanje.getTip().equalsIgnoreCase("praktikum")) {
                        row.add(predavanje.getPredmet().getFond_praktikum().toString());
                        ukupnoPredmet += predavanje.getPredmet().getFond_praktikum() * predavanje.getBr_termina();
                    }
                } else {
                    if (predavanje.getTip().equalsIgnoreCase("vezbe")) {
                        row.add(predavanje.getPredmet().getFond_vezbe().toString());
                        ukupnoPredmet += predavanje.getPredmet().getFond_vezbe() * predavanje.getBr_termina();

                    }
                    if (predavanje.getTip().equalsIgnoreCase("praktikum")) {
                        row.add(predavanje.getPredmet().getFond_praktikum().toString());
                        ukupnoPredmet += predavanje.getPredmet().getFond_praktikum() * predavanje.getBr_termina();

                    }
                }
                if (predavanje.getTip().equalsIgnoreCase("predavanje")) {
                    row.add(predavanje.getPredmet().getFond_predavanja().toString());
                    ukupnoPredmet += predavanje.getPredmet().getFond_predavanja() * predavanje.getBr_termina();
                }

                //ukupno za predmet
                row.add(String.valueOf(ukupnoPredmet));

                ukupnoProf += ukupnoPredmet;

                if (isParanSemestar(predavanje)) {
                    ukupnoParni += ukupnoPredmet;
                } else {
                    ukupnoNeparni += ukupnoPredmet;
                }

                this.rows.add(row);
            }

            nedeljaRow.add("");
            nedeljaRow.add("");
            nedeljaRow.add("");
            nedeljaRow.add("");
            nedeljaRow.add("");
            nedeljaRow.add("Nedeljno");
            nedeljaRow.add(String.valueOf(ukupnoProf));
            this.rows.add(nedeljaRow);
            semestarRow.add("");
            semestarRow.add("");
            semestarRow.add("");
            semestarRow.add("");
            semestarRow.add("");
            semestarRow.add("Semestri");
            ukupnoNeparni *= skolskaGodina.getBrNedeljaUSemestru();
            ukupnoParni *= skolskaGodina.getBrNedeljaUSemestru();
            semestarRow.add(ukupnoNeparni + "-" + ukupnoParni);
            this.rows.add(semestarRow);
            godinaRow.add("");
            godinaRow.add("");
            godinaRow.add("");
            godinaRow.add("");
            godinaRow.add("");
            godinaRow.add("Godisnje");
            ukupnoGodina = (ukupnoProf * skolskaGodina.getBrNedeljaUSemestru());
            godinaRow.add(String.valueOf(ukupnoGodina));
            this.rows.add(godinaRow);

        }


    }

    public void makeFullTableContent(List<Predavac> predavacList, SkolskaGodina skolskaGodina) {
        setProfesorColumns();


        boolean firstRow;
        for (Predavac predavac : predavacList) {
            System.out.println("PREDAVAC " + predavac.getName() + predavac.getLastname());
            double ukupnoProf = 0.0;
            double ukupnoParni = 0.0;
            double ukupnoNeparni = 0.0;
            double ukupnoVanredni = 0.0;
            double ukupnoGodina = 0.0;
            firstRow = true;

            //ako nisu iste skolske godine
            predavac.getPredavanja().removeIf(predavanje -> predavanje.getSkolskaGodina().getPocetnaGodina().intValue() != skolskaGodina.getPocetnaGodina().intValue());
            //ako nisu iste skolske godine
            predavac.getVanredniCasovi().removeIf(vanredniCas -> vanredniCas.getSkolskaGodina().getPocetnaGodina().intValue() != skolskaGodina.getPocetnaGodina().intValue());
            if (predavac.getPredavanja().isEmpty() && predavac.getVanredniCasovi().isEmpty()) {
                continue;
            }

            List<String> nedeljaRow = new ArrayList<>();
            List<String> semestarRow = new ArrayList<>();
            List<String> vanredniRow = new ArrayList<>();
            List<String> godinaRow = new ArrayList<>();

            for (Predavanje predavanje : predavac.getPredavanja()) {
                List<String> row = new ArrayList<>();
                double ukupnoPredmet = 0.0;
                //ime prof
                row.add(firstRow ? predavac.getName() + " " + predavac.getLastname() : "");
                firstRow = false;
                //ime predmeta
                row.add(predavanje.getPredmet().getNaziv());
                //tip predavanja
                row.add(predavanje.getTip());
                //semestar
                row.add(predavanje.getPredmet().getSemestar().toString());
                //br termina
                row.add(predavanje.getBr_termina().toString());
                //racunati casovi
                if (predavac.getType().equalsIgnoreCase("profesor")) {
                    if (predavanje.getTip().equalsIgnoreCase("vezbe")) {
                        row.add(predavanje.getPredmet().getFond_vezbe() + "/2");
                        ukupnoPredmet += predavanje.getPredmet().getFond_vezbe() / 2.0 * predavanje.getBr_termina();
                    }
                    if (predavanje.getTip().equalsIgnoreCase("praktikum")) {
                        row.add(predavanje.getPredmet().getFond_praktikum() + "/2");
                        ukupnoPredmet += predavanje.getPredmet().getFond_praktikum() / 2.0 * predavanje.getBr_termina();
                    }
                } else {
                    if (predavanje.getTip().equalsIgnoreCase("vezbe")) {
                        row.add(predavanje.getPredmet().getFond_vezbe().toString());
                        ukupnoPredmet += predavanje.getPredmet().getFond_vezbe() * predavanje.getBr_termina();

                    }
                    if (predavanje.getTip().equalsIgnoreCase("praktikum")) {
                        row.add(predavanje.getPredmet().getFond_praktikum().toString());
                        ukupnoPredmet += predavanje.getPredmet().getFond_praktikum() * predavanje.getBr_termina();

                    }
                }
                if (predavanje.getTip().equalsIgnoreCase("predavanje")) {
                    row.add(predavanje.getPredmet().getFond_predavanja().toString());
                    ukupnoPredmet += predavanje.getPredmet().getFond_predavanja() * predavanje.getBr_termina();
                }

                //ukupno za predmet
                row.add(String.valueOf(ukupnoPredmet));

                ukupnoProf += ukupnoPredmet;

                if (isParanSemestar(predavanje)) {
                    ukupnoParni += ukupnoPredmet;
                } else {
                    ukupnoNeparni += ukupnoPredmet;
                }

                this.rows.add(row);
            }
            //VANREDNI CASOVI
            for (VanredniCas vanredniCas : predavac.getVanredniCasovi()) {
                List<String> row = new ArrayList<>();
                //profesor
                row.add(firstRow ? predavac.getName() + " " + predavac.getLastname() : "");
                firstRow = false;
                //predmet
                row.add(vanredniCas.getOpis());
                //tip
                row.add("");
                //semestar
                row.add("");
                //br termina
                row.add("");
                //br casova
                row.add(vanredniCas.getBr_casova().toString());
                //ukupno casova za vanredni predmet
                row.add(vanredniCas.getBr_casova().toString());
                ukupnoVanredni += vanredniCas.getBr_casova();

                this.rows.add(row);

            }
            nedeljaRow.add("");
            nedeljaRow.add("");
            nedeljaRow.add("");
            nedeljaRow.add("");
            nedeljaRow.add("");
            nedeljaRow.add("Nedeljno");
            nedeljaRow.add(String.valueOf(ukupnoProf));
            this.rows.add(nedeljaRow);
            semestarRow.add("");
            semestarRow.add("");
            semestarRow.add("");
            semestarRow.add("");
            semestarRow.add("");
            semestarRow.add("Semestri");
            ukupnoNeparni *= skolskaGodina.getBrNedeljaUSemestru();
            ukupnoParni *= skolskaGodina.getBrNedeljaUSemestru();
            semestarRow.add(ukupnoNeparni + "-" + ukupnoParni);
            this.rows.add(semestarRow);
            vanredniRow.add("");
            vanredniRow.add("");
            vanredniRow.add("");
            vanredniRow.add("");
            vanredniRow.add("");
            vanredniRow.add("Vanredni");
            vanredniRow.add(String.valueOf(ukupnoVanredni));
            this.rows.add(vanredniRow);
            godinaRow.add("");
            godinaRow.add("");
            godinaRow.add("");
            godinaRow.add("");
            godinaRow.add("");
            godinaRow.add("Godisnje");
            ukupnoGodina = (ukupnoProf * skolskaGodina.getBrNedeljaUSemestru()) + ukupnoVanredni;
            godinaRow.add(String.valueOf(ukupnoGodina));
            this.rows.add(godinaRow);


        }


    }


    public void makePredmetTableContent(List<Predmet> predmetList, SkolskaGodina skolskaGodina) {
        setPredmetColumns();

        boolean firstRow;
        for (Predmet predmet : predmetList) {
            firstRow = true;
            predmet.getPredavanja().removeIf(predavanje -> predavanje.getSkolskaGodina().getPocetnaGodina().intValue() != skolskaGodina.getPocetnaGodina().intValue());

            if (predmet.getPredavanja().isEmpty()) {
                continue;
            }

            for (Predavanje predavanje : predmet.getPredavanja()) {
                List<String> row = new ArrayList<>();
                //ime predmeta
                row.add(firstRow ? predmet.getNaziv() : "");
                firstRow = false;
                //ime profesora
                row.add(predavanje.getPredavac().getName() + " " + predavanje.getPredavac().getLastname());
                //tip predavanja
                row.add(predavanje.getTip());
                //semestar
                row.add(predmet.getSemestar().toString());
                //br termina
                row.add(predavanje.getBr_termina().toString());
                //racunati casovi
                if (predavanje.getPredavac().getType().equalsIgnoreCase("profesor")) {
                    if (predavanje.getTip().equalsIgnoreCase("vezbe")) {
                        row.add(predavanje.getPredmet().getFond_vezbe() + "/2");
                    }
                    if (predavanje.getTip().equalsIgnoreCase("praktikum")) {
                        row.add(predavanje.getPredmet().getFond_praktikum() + "/2");
                    }
                } else {
                    if (predavanje.getTip().equalsIgnoreCase("vezbe")) {
                        row.add(predavanje.getPredmet().getFond_vezbe().toString());

                    }
                    if (predavanje.getTip().equalsIgnoreCase("praktikum")) {
                        row.add(predavanje.getPredmet().getFond_praktikum().toString());

                    }
                }
                if (predavanje.getTip().equalsIgnoreCase("predavanje")) {
                    row.add(predavanje.getPredmet().getFond_predavanja().toString());
                }

                this.rows.add(row);

            }

        }

    }

    private void setProfesorColumns() {
        this.columns.add("Profesor");
        this.columns.add("Predmet");
        this.columns.add("tip");
        this.columns.add("sem");
        this.columns.add("termini");
        this.columns.add("casovi");
        this.columns.add("Ukupno");
    }

    private void setPredmetColumns() {
        this.columns.add("Predmet");
        this.columns.add("Profesor");
        this.columns.add("tip");
        this.columns.add("sem");
        this.columns.add("termini");
        this.columns.add("casovi");
    }

    private boolean isParanSemestar(Predavanje predavanje) {
        return predavanje.getPredmet().getSemestar().intValue() % 2 == 0;
    }
}
