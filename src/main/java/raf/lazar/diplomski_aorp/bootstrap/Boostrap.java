package raf.lazar.diplomski_aorp.bootstrap;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import raf.lazar.diplomski_aorp.model.Predavac;
import raf.lazar.diplomski_aorp.model.Predavanje;
import raf.lazar.diplomski_aorp.model.Predmet;
import raf.lazar.diplomski_aorp.model.SkolskaGodina;
import raf.lazar.diplomski_aorp.repositories.*;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

@Component
@AllArgsConstructor
public class Boostrap implements CommandLineRunner {

    private PredavacRepository predavacRepository;
    private PredavanjeRepository predavanjeRepository;
    private PredmetRepository predmetRepository;
    private TerminiRepository terminiRepository;
    private VanredniCasRepository vanredniCasRepository;
    private SkolskaGodinaRepository skolskaGodinaRepository;



    @Override
    public void run(String... args) throws Exception {
        System.out.println("ITS ALIVE");

//        boolean load = true;
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Ucitaj podatke iz fajlova u bazu? true/false\nPreporuka: ne, osim ako je baza prazna");
//        load = scanner.nextBoolean();
//
//        if (load) {
//
//            //kreiraj skolsku godinu
//            SkolskaGodina skolskaGodina = new SkolskaGodina();
//            skolskaGodina.setKrajnjaGodina(2024);
//            skolskaGodina.setPocetnaGodina(2023);
//            skolskaGodina.setBrNedeljaUSemestru(13);
//            this.skolskaGodinaRepository.save(skolskaGodina);
//            SkolskaGodina skolskaGodina1 = new SkolskaGodina();
//            skolskaGodina1.setKrajnjaGodina(2023);
//            skolskaGodina1.setPocetnaGodina(2022);
//            skolskaGodina1.setBrNedeljaUSemestru(13);
//            this.skolskaGodinaRepository.save(skolskaGodina1);
//            SkolskaGodina skolskaGodina2 = new SkolskaGodina();
//            skolskaGodina2.setKrajnjaGodina(2022);
//            skolskaGodina2.setPocetnaGodina(2021);
//            skolskaGodina2.setBrNedeljaUSemestru(13);
//            this.skolskaGodinaRepository.save(skolskaGodina2);
//
//            //LOAD FILES
//            File predavaci = new File("C:\\Users\\Administrator\\Documents\\RAF projekti\\Diplomski Lazar Popovic\\Diplomski-AORP-Back\\src\\main\\resources\\nastavnici.xlsx");
//            File predmeti = new File("C:\\Users\\Administrator\\Documents\\RAF projekti\\Diplomski Lazar Popovic\\Diplomski-AORP-Back\\src\\main\\resources\\predmeti.xlsx");
//            File programi = new File("C:\\Users\\Administrator\\Documents\\RAF projekti\\Diplomski Lazar Popovic\\Diplomski-AORP-Back\\src\\main\\resources\\stud_programi.xlsx");
//            //WORK ON EXCEL
//            FileInputStream predavaciInputStream = null;
//            FileInputStream predmetiInputStream = null;
//            FileInputStream programiInputStream = null;
//            Workbook workbookPredavaci = null;
//            Workbook workbookPredmeti = null;
//            Workbook workbookProgrami = null;
//            try {
//                predavaciInputStream = new FileInputStream(predavaci.getAbsolutePath());
//                predmetiInputStream = new FileInputStream(predmeti.getAbsolutePath());
//                programiInputStream = new FileInputStream(programi.getAbsolutePath());
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            try {
//                assert predavaciInputStream != null;
//                assert predmetiInputStream != null;
//                assert programiInputStream != null;
//                workbookPredavaci = new XSSFWorkbook(predavaciInputStream);
//                workbookPredmeti = new XSSFWorkbook(predmetiInputStream);
//                workbookProgrami = new XSSFWorkbook(programiInputStream);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            //UCITAJ PREDAVACE
//            assert workbookPredavaci != null;
//            System.out.println("loading predavaci");
//            Sheet sheet = workbookPredavaci.getSheetAt(0);
//            loadPredavaci(workbookPredavaci);
//            System.out.println("finished loading predavaci");
//
//            //UCITAJ PREDMETE I SETUJ IM TIP
//            System.out.println("loading programi");
//            HashMap<Integer, String> programiMap = loadProgrami(workbookProgrami);
//            System.out.println("finished loading programi");
//
//            System.out.println("loading predmeti");
//            loadPredmeti(workbookPredmeti, programiMap);
//            System.out.println("DATA LOADED");
//        }
        System.out.println("APP STARTED");

    }

    private void loadPredmeti(Workbook workbookPredmeti, HashMap<Integer, String> programiMap) {
        Sheet sheet = workbookPredmeti.getSheetAt(0);
        DataFormatter dataFormatter = new DataFormatter();
        Row header = sheet.getRow(0);
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            Predmet predmet = new Predmet();
            for (Cell cell : row) {
                if (header.getCell(cell.getColumnIndex()).getRichStringCellValue().getString().equalsIgnoreCase("espb")) {
                    predmet.setEspb(Integer.parseInt(dataFormatter.formatCellValue(cell)));
                    continue;
                }
                if (header.getCell(cell.getColumnIndex()).getRichStringCellValue().getString().equalsIgnoreCase("fond_predavanja")) {
                    predmet.setFond_predavanja(Integer.parseInt(dataFormatter.formatCellValue(cell)));
                    continue;
                }
                if (header.getCell(cell.getColumnIndex()).getRichStringCellValue().getString().equalsIgnoreCase("fond_vezbe")) {
                    predmet.setFond_vezbe(Integer.parseInt(dataFormatter.formatCellValue(cell)));
                    continue;
                }
                if (header.getCell(cell.getColumnIndex()).getRichStringCellValue().getString().equalsIgnoreCase("fond_praktikum")) {
                    predmet.setFond_praktikum(Integer.parseInt(dataFormatter.formatCellValue(cell)));
                    continue;
                }
                if (header.getCell(cell.getColumnIndex()).getRichStringCellValue().getString().equalsIgnoreCase("naziv")) {
                    predmet.setNaziv(dataFormatter.formatCellValue(cell));
                    continue;
                }
                if (header.getCell(cell.getColumnIndex()).getRichStringCellValue().getString().equalsIgnoreCase("semestar")) {
                    predmet.setSemestar(Integer.parseInt(dataFormatter.formatCellValue(cell)));
                    continue;
                }
                if (header.getCell(cell.getColumnIndex()).getRichStringCellValue().getString().equalsIgnoreCase("stud_program_id")) {
                    predmet.setTip_studija(programiMap.get(Integer.parseInt(dataFormatter.formatCellValue(cell))));
                }
            }
            if (predmet.getFond_praktikum() != null && predmet.getFond_predavanja() != null && predmet.getFond_vezbe() != null
                    && predmet.getNaziv() != null && predmet.getEspb() != null && predmet.getSemestar() != null && predmet.getTip_studija() != null) {
                System.out.println("SAVING PREDMET " + predmet);
                this.predmetRepository.save(predmet);
            }
        }
    }

    private HashMap<Integer, String> loadProgrami(Workbook workbookProgrami) {
        HashMap<Integer, String> res = new HashMap<>();
        Sheet sheet = workbookProgrami.getSheetAt(0);
        Row header = sheet.getRow(0);
        DataFormatter dataFormatter = new DataFormatter();
        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            int id = -1;
            String s = "";
            for (Cell cell : row) {
                if (header.getCell(cell.getColumnIndex()).getRichStringCellValue().getString().equalsIgnoreCase("id")) {
                    id = Integer.parseInt(dataFormatter.formatCellValue(cell));
                }
                if (header.getCell(cell.getColumnIndex()).getRichStringCellValue().getString().equalsIgnoreCase("vrsta_studija")) {
                    s = dataFormatter.formatCellValue(cell);
                }
            }
            if (id != -1 && s.length() > 0) {
                res.put(id, s);
            }
        }

        return res;
    }

    private void loadPredavaci(Workbook workbookPredavaci) {
        Sheet sheet = workbookPredavaci.getSheetAt(0);
        HashMap<String, Integer> map = new HashMap<>();
        int totalCnt = 0;
        ArrayList<Predavac> predavacArrayList = new ArrayList<>();
        for (Row row : sheet) {
            System.out.println("CURRENTLY ON ROW " + row.getRowNum());
            String msg = "Error parsing row " + row.getRowNum();
            try {
                if (row.getRowNum() == 0) {
                    for (Cell cell : row) {
                        if (cell.getRichStringCellValue().getString().equalsIgnoreCase("ime")) {
                            map.put("ime", cell.getColumnIndex());
                        }
                        if (cell.getRichStringCellValue().getString().equalsIgnoreCase("prezime")) {
                            map.put("prezime", cell.getColumnIndex());
                        }
                        if (cell.getRichStringCellValue().getString().equalsIgnoreCase("tip")) {
                            map.put("tip", cell.getColumnIndex());
                        }
                        if (cell.getRichStringCellValue().getString().equalsIgnoreCase("email")) {
                            map.put("email", cell.getColumnIndex());
                        }
                    }
                    continue;
                }

                Predavac predavac = getBasicPredavacData(row, map);
                //ako smo vec ucitali predavaca, .equals koristi name, lastname i email za proveru
                if (predavacArrayList.contains(predavac)) {
                    Predavac p1 = predavacArrayList.get(predavacArrayList.indexOf(predavac));
                    //ako istom predavacu nije isti tip
                    if (!p1.getType().equals(predavac.getType())) {
                        //stavi mu tip na korektan tip, ne mora da se doda u listu posto je predavac duplikat od p1
                        if (p1.getType().equalsIgnoreCase("profesor") || predavac.getType().equalsIgnoreCase("profesor")) {
                            p1.setType("Profesor");
                        } else if (p1.getType().equalsIgnoreCase("saradnik") || predavac.getType().equalsIgnoreCase("saradnik")) {
                            p1.setType("Saradnik");
                        }

                    }
                } else {
                    predavacArrayList.add(predavac);
                }
                totalCnt++;
//            System.out.println(totalCnt);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, msg + " " + e.getMessage(), "Dialog", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
        for (Predavac p : predavacArrayList) {
            System.out.println("SAVING PREDAVAC " + p.toString());
            this.predavacRepository.save(p);
        }

    }

    private Predavac getBasicPredavacData(Row row, HashMap<String, Integer> map) {
        Predavac result = new Predavac();
        DataFormatter dataFormatter = new DataFormatter();
        for (Cell cell : row) {
            if (cell.getColumnIndex() == map.get("ime").intValue()) {
                result.setName(dataFormatter.formatCellValue(cell));
                continue;
            }
            if (cell.getColumnIndex() == map.get("prezime").intValue()) {
                result.setLastname(dataFormatter.formatCellValue(cell));
                continue;
            }
            if (cell.getColumnIndex() == map.get("tip").intValue()) {
                result.setType(dataFormatter.formatCellValue(cell));
                continue;
            }
            if (cell.getColumnIndex() == map.get("email").intValue()) {
                result.setEmail(dataFormatter.formatCellValue(cell));
            }
        }
        return result;
    }

}
