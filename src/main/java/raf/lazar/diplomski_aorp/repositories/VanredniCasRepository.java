package raf.lazar.diplomski_aorp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import raf.lazar.diplomski_aorp.model.VanredniCas;

@Repository
public interface VanredniCasRepository extends JpaRepository<VanredniCas, Long> {
}
