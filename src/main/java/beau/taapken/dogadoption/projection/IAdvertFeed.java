package beau.taapken.dogadoption.projection;

import beau.taapken.dogadoption.enumerator.DogBreed;

public interface IAdvertFeed {
    String getAdvertId();
    String getImage();
    String getTitle();
    DogBreed getBreed();
    int getAge();
}
