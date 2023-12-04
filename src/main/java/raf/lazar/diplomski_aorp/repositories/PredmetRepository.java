package raf.lazar.diplomski_aorp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.lazar.diplomski_aorp.model.Predmet;

import java.util.List;

@Repository
public interface PredmetRepository extends JpaRepository<Predmet, Long> {

    List<Predmet> findAllByPredavanjaIsNotEmpty();
}
