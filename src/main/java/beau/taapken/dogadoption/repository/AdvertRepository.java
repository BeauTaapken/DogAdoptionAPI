package beau.taapken.dogadoption.repository;

import beau.taapken.dogadoption.model.Advert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertRepository extends JpaRepository<Advert, String> {

}
