package beau.taapken.dogadoption.repository;

import beau.taapken.dogadoption.enumerator.DogBreed;
import beau.taapken.dogadoption.model.Advert;
import beau.taapken.dogadoption.projection.IAdvertFeed;
import beau.taapken.dogadoption.projection.IGetAdvert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface AdvertRepository extends JpaRepository<Advert, String> {
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Advert advert SET advert.age = :age, advert.breed = :breed, advert.description = :description, advert.image = :image, advert.title = :title, advert.latitude = :latitude, advert.longtitude = :longtitude, advert.place = :place WHERE advert.advertId = :advertId")
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    void updateAdvert(String advertId, int age, DogBreed breed, String description, String image, String title, float latitude, float longtitude, String place);

    List<IAdvertFeed> findAllByOrderByDateTimeDesc(Pageable pageable);

    IGetAdvert findByAdvertId(String advertId);
}
