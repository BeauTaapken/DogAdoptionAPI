package beau.taapken.dogadoption.projection;

import beau.taapken.dogadoption.enumerator.DogBreed;
import beau.taapken.dogadoption.model.User;

public interface IGetAdvert {
    String getAdvertId();
    User getUser();
    String getImage();
    String getTitle();
    String getDescription();
    DogBreed getBreed();
    int getAge();
    float getLongtitude();
    float getLatitude();
    String getPlace();
}
