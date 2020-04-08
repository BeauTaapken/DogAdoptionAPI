package beau.taapken.dogadoption.logic;

import beau.taapken.dogadoption.enumerator.ResponseCode;
import beau.taapken.dogadoption.interfaces.IAdvert;
import beau.taapken.dogadoption.model.Advert;
import beau.taapken.dogadoption.model.Response;
import beau.taapken.dogadoption.repository.AdvertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdvertLogic implements IAdvert {
    private AdvertRepository advertRepository;

    @Autowired
    public AdvertLogic(AdvertRepository advertRepository) { this.advertRepository = advertRepository; }

    public Response addAdvert(Advert advert) {
        Response response = new Response(ResponseCode.Error, "Placeholder");
        try{
            advert.setDateTime(LocalDateTime.now());
            advertRepository.save(advert);
            response.setResponseCode(ResponseCode.Done);
            response.setResponseDescription("Everything went correctly");
        }
        catch(Exception ex){
            response.setResponseDescription(ex.toString());
        }
        return response;
    }

    public List<Advert> getAdverts(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return advertRepository.findAllByOrderByDateTimeDesc(pageable);
    }
}
