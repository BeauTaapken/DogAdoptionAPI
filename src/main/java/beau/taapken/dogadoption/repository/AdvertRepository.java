package beau.taapken.dogadoption.repository;

import beau.taapken.dogadoption.model.Advert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface AdvertRepository extends JpaRepository<Advert, String> {
    @Modifying
    @Query("UPDATE Advert advert SET advert.age = :age, advert.breed = :breed, advert.description = :description, advert.image = :image, advert.title = :title, advert.latitude = :latitude, advert.longtitude = :longtitude, advert.place = :place WHERE advert.advertId = :advertId")
    void updateAdvert(String advertId, int age, int breed, String description, String image, String title, float latitude, float longtitude, String place);

    List<Advert> findAllByOrderByDateTimeAsc(Pageable pageable);
}
