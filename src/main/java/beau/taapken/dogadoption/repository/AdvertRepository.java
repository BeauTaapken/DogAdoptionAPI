package beau.taapken.dogadoption.repository;

import beau.taapken.dogadoption.model.Advert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface AdvertRepository extends JpaRepository<Advert, String> {
    List<Advert> findAllByOrderByDateTimeDesc(Pageable pageable);
}
