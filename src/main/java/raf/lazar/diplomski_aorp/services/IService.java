package raf.lazar.diplomski_aorp.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IService<T, ID> {
    <S extends T> S save(S var1);

    Optional<T> findById(ID var1);

    List<T> findAll();

    void deleteById(ID var1);

}
