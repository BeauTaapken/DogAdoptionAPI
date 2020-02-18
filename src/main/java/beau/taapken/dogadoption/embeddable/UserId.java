package beau.taapken.dogadoption.embeddable;

import java.io.Serializable;

public class UserId implements Serializable {
    protected String UUID;
    protected String Username;

    public UserId() {}

    public UserId(String UUID, String Username){
        this.UUID = UUID;
        this.Username = Username;
    }
}
