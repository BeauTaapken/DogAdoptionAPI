package beau.taapken.dogadoption.controller;

import beau.taapken.dogadoption.enumerator.ResponseCode;
import beau.taapken.dogadoption.interfac.IAdvert;
import beau.taapken.dogadoption.logic.AdvertLogic;
import beau.taapken.dogadoption.logic.VerificationLogic;
import beau.taapken.dogadoption.model.Advert;
import beau.taapken.dogadoption.model.Response;
import com.google.firebase.auth.FirebaseAuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RequestMapping("/advert")
@RestController
public class AdvertController implements IAdvert {
    @Autowired
    private AdvertLogic advertLogic;
    @Autowired
    private VerificationLogic verificationLogic;

    @PostMapping("/addadvert")
    public Response addAdvert(@RequestHeader String id, @RequestBody Advert advert) throws FirebaseAuthException {
        if(verificationLogic.isUser(id)){
            try{
                return advertLogic.addAdvert(advert);
            }
            catch (Exception ex){
                return new Response(ResponseCode.Error, ex.toString());
            }
        }
        return new Response(ResponseCode.Error, "Account could not be found. Please don't use this api if you haven't added a account.");
    }
}
