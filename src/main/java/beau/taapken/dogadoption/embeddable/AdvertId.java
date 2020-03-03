package beau.taapken.dogadoption.embeddable;

import java.io.Serializable;

public class AdvertId implements Serializable {
    protected String UUID;
    protected int advertId;

    public AdvertId() {}

    public AdvertId(String UUID, int advertId){
        this.UUID = UUID;
        this.advertId = advertId;
    }
}
