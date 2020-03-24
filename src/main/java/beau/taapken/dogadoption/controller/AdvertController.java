package beau.taapken.dogadoption.controller;

import beau.taapken.dogadoption.enumerator.ResponseCode;
import beau.taapken.dogadoption.interfac.IAdvert;
import beau.taapken.dogadoption.logic.AdvertLogic;
import beau.taapken.dogadoption.logic.VerificationLogic;
import beau.taapken.dogadoption.model.Advert;
import beau.taapken.dogadoption.model.Response;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RequestMapping("/advert")
@RestController
public class AdvertController implements IAdvert {
    @Autowired
    private AdvertLogic advertLogic;
    @Autowired
    private VerificationLogic verificationLogic;

    @PostMapping(value = "/addadvert")
    public Response addAdvert(@RequestHeader String id, @RequestBody Advert advert) {
        if(verificationLogic.isUser(id)){
            try{
                System.out.println(id);
                return advertLogic.addAdvert(advert);
            }
            catch (Exception ex){
                return new Response(ResponseCode.Error, ex.toString());
            }
        }
        return new Response(ResponseCode.Error, "Account could not be found. Please don't use this api if you haven't added a account.");
    }

    @GetMapping("/getAdverts")
    public List<Advert> getAdverts(@Param("page") int page, @Param("size") int size){
        List<Advert> t = advertLogic.getAdverts(page, size);
        return t;
    }
}
