package raf.lazar.diplomski_aorp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.lazar.diplomski_aorp.model.SkolskaGodina;

@Repository
public interface SkolskaGodinaRepository extends JpaRepository<SkolskaGodina, Long> {

    SkolskaGodina findSkolskaGodinaByPocetnaGodinaAndKrajnjaGodina(Integer pocetna_godina, Integer krajnja_godina);
}
