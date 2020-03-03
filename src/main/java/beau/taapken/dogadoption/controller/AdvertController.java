package beau.taapken.dogadoption.controller;

import beau.taapken.dogadoption.enumerator.ResponseCode;
import beau.taapken.dogadoption.interfac.IAdvert;
import beau.taapken.dogadoption.logic.AdvertLogic;
import beau.taapken.dogadoption.model.Advert;
import beau.taapken.dogadoption.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("/advert")
@RestController
public class AdvertController implements IAdvert {
    @Autowired
    private AdvertLogic advertLogic;

    @PostMapping("/addadvert")
    public Response addAdvert(@RequestBody Advert advert){
        try{
            return advertLogic.addAdvert(advert);
        }
        catch (Exception ex){
            return new Response(ResponseCode.Error, ex.toString());
        }
    }
}
