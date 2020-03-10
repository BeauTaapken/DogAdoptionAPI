package beau.taapken.dogadoption.logic;

import beau.taapken.dogadoption.enumerator.ResponseCode;
import beau.taapken.dogadoption.interfac.IAdvert;
import beau.taapken.dogadoption.model.Advert;
import beau.taapken.dogadoption.model.Response;
import beau.taapken.dogadoption.repository.AdvertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdvertLogic implements IAdvert {
    private AdvertRepository advertRepository;

    @Autowired
    public AdvertLogic(AdvertRepository advertRepository) { this.advertRepository = advertRepository; }

    public Response addAdvert(Advert advert) {
        Response response = new Response(ResponseCode.Error, "Placeholder");
        try{
            advertRepository.save(advert);
            response.setResponseCode(ResponseCode.Done);
            response.setResponseDescription("Everything went correctly");
        }
        catch(Exception ex){
            response.setResponseDescription(ex.toString());
        }
        return response;
    }
}
