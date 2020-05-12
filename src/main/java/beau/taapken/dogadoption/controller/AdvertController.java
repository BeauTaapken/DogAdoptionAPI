package beau.taapken.dogadoption.controller;

import beau.taapken.dogadoption.enumerator.ResponseCode;
import beau.taapken.dogadoption.interfaces.IAdvert;
import beau.taapken.dogadoption.logic.AdvertLogic;
import beau.taapken.dogadoption.logic.VerificationLogic;
import beau.taapken.dogadoption.model.Advert;
import beau.taapken.dogadoption.model.Response;
import beau.taapken.dogadoption.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("/advert")
@RestController
@Log4j2
public class AdvertController implements IAdvert {
    @Autowired
    private AdvertLogic advertLogic;
    @Autowired
    private VerificationLogic verificationLogic;

    @PostMapping(value = "/addadvert")
    public Response addAdvert(@RequestHeader String firebaseToken, @RequestBody Advert advert) {
        String UUID = verificationLogic.GetUUID(firebaseToken);
        if(UUID != null){
            advert.setUser(new User(UUID, null));
            try{
                return advertLogic.addAdvert(advert);
            }
            catch (Exception ex){
                log.error(ex);
                return new Response(ResponseCode.Error, ex.toString());
            }
        }
        return new Response(ResponseCode.Error, "Account could not be found. Please don't use this api if you haven't added a account.");
    }

    @GetMapping("/getadverts")
    public List<Advert> getAdverts(@Param("page") int page, @Param("size") int size){
        return advertLogic.getAdverts(page, size);
    }

    @PutMapping("/updateadvert")
    public Response updateAdvert(@RequestHeader String firebaseToken, @RequestBody Advert advert){
        String UUID = verificationLogic.GetUUID(firebaseToken);
        if(UUID != null){
            if(advertLogic.userIsAdvertOwner(advert.getAdvertId(), UUID)){
                return advertLogic.updateAdvert(advert);
            }
            return new Response(ResponseCode.Error, "You are not allowed to update another users advert");
        }
        return new Response(ResponseCode.Error, "You need to have an account to update your adverts");
    }

    @DeleteMapping("/deleteadvert")
    public Response deleteAdvert(@RequestHeader String firebaseToken, @RequestHeader String advertId) {
        String UUID = verificationLogic.GetUUID(firebaseToken);
        if(UUID != null) {
            if (advertLogic.userIsAdvertOwner(advertId, UUID)) {
                return advertLogic.deleteAdvert(advertId);
            }
            return new Response(ResponseCode.Error, "You are not allowed to delete another users advert");
        }
        return new Response(ResponseCode.Error, "You need to have an account to delete your adverts");
    }
}
