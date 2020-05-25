package beau.taapken.dogadoption.controller;

import beau.taapken.dogadoption.logic.AdvertLogic;
import beau.taapken.dogadoption.logic.VerificationLogic;
import beau.taapken.dogadoption.model.Advert;
import beau.taapken.dogadoption.model.User;
import beau.taapken.dogadoption.projection.IGetAdvert;
import beau.taapken.dogadoption.projection.IAdvertFeed;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("/advert")
@RestController
@Log4j2
public class AdvertController {
    @Autowired
    private AdvertLogic advertLogic;
    @Autowired
    private VerificationLogic verificationLogic;

    @PostMapping(value = "/addadvert")
    public ResponseEntity addAdvert(@RequestHeader String firebaseToken, @RequestBody Advert advert) {
        String UUID = verificationLogic.GetUUID(firebaseToken);
        if(UUID != null){
            advert.setUser(new User(UUID, null));
            try{
                return advertLogic.addAdvert(advert);
            }
            catch (Exception ex){
                log.error(ex);
                return new ResponseEntity<>(ex.toString(), HttpStatus.REQUEST_TIMEOUT);
            }
        }
        return new ResponseEntity<>("Account could not be found. Please don't use this api if you haven't added a account.", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/getadvert/{advertId}")
    public IGetAdvert getAdvert(@PathVariable String advertId){
        return advertLogic.getAdvert(advertId);
    }

    @GetMapping("/getadvertpreviews")
    public List<IAdvertFeed> getAdvertPreviews(@Param("page") int page, @Param("size") int size){
        return advertLogic.getAdvertPreviews(page, size);
    }

    @PutMapping("/updateadvert")
    public ResponseEntity updateAdvert(@RequestHeader String firebaseToken, @RequestBody Advert advert){
        String UUID = verificationLogic.GetUUID(firebaseToken);
        if(UUID != null){
            if(advertLogic.userIsAdvertOwner(advert.getAdvertId(), UUID)){
                return advertLogic.updateAdvert(advert);
            }
            return new ResponseEntity<>("You are not allowed to update another users advert", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("You need to have an account to update your adverts", HttpStatus.FORBIDDEN);
    }

    @DeleteMapping("/deleteadvert")
    public ResponseEntity deleteAdvert(@RequestHeader String firebaseToken, @RequestHeader String advertId) {
        String UUID = verificationLogic.GetUUID(firebaseToken);
        if(UUID != null) {
            if (advertLogic.userIsAdvertOwner(advertId, UUID)) {
                return advertLogic.deleteAdvert(advertId);
            }
            return new ResponseEntity<>("You are not allowed to delete another users advert", HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>("You need to have an account to delete your adverts", HttpStatus.FORBIDDEN);
    }
}
