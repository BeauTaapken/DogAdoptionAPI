package beau.taapken.dogadoption.logic;

import beau.taapken.dogadoption.enumerator.DogBreed;
import beau.taapken.dogadoption.helper.EnumUtils;
import beau.taapken.dogadoption.interfaces.IEnum;
import org.springframework.stereotype.Service;

@Service
public class EnumLogic implements IEnum {
    public String getDogBreeds() {
        return EnumUtils.getStringValues(DogBreed.class);
    }
}
