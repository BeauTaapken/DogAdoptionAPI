package beau.taapken.dogadoption.logic;

import beau.taapken.dogadoption.model.Advert;
import beau.taapken.dogadoption.projection.IAdvertFeed;
import beau.taapken.dogadoption.repository.AdvertRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Log4j2
public class AdvertLogic {
    private AdvertRepository advertRepository;

    @Autowired
    public AdvertLogic(AdvertRepository advertRepository) { this.advertRepository = advertRepository; }

    public ResponseEntity addAdvert(Advert advert) {
        try{
            advert.setDateTime(LocalDateTime.now());
            advertRepository.save(advert);
            return new ResponseEntity<>("Everything went correctly", HttpStatus.OK);
        }
        catch(Exception ex){
            log.error(ex);
            return new ResponseEntity<>(ex.toString(), HttpStatus.REQUEST_TIMEOUT);
        }
    }

    public Advert getAdvert(String advertId){
        return advertRepository.findByAdvertId(advertId);
    }

    public List<IAdvertFeed> getAdvertPreviews(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return advertRepository.findAllByOrderByDateTimeDesc(pageable);
    }

    public ResponseEntity updateAdvert(Advert advert){
        try{
            advertRepository.updateAdvert(advert.getAdvertId(), advert.getAge(), advert.getBreed(), advert.getDescription(), advert.getImage(), advert.getTitle(), advert.getLatitude(), advert.getLongtitude(), advert.getPlace());
            return new ResponseEntity<>("Everything went correctly", HttpStatus.OK);
        }
        catch(Exception ex){
            log.error(ex);
            return new ResponseEntity<>(ex.toString(), HttpStatus.REQUEST_TIMEOUT);
        }
    }

    public ResponseEntity deleteAdvert(String id){
        try {
            advertRepository.deleteById(id);
            return new ResponseEntity<>("Everything went correctly", HttpStatus.OK);
        }
        catch (Exception ex){
            log.error(ex);
            return new ResponseEntity<>(ex.toString(), HttpStatus.REQUEST_TIMEOUT);
        }
    }

    public boolean userIsAdvertOwner(String id, String UUID){
        Advert advert = advertRepository.getOne(id);
        return UUID.equals(advert.getUser().getUUID());
    }
}
