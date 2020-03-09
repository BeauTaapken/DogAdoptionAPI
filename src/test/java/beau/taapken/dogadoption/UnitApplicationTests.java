package beau.taapken.dogadoption;

import beau.taapken.dogadoption.enumerator.DogBreed;
import beau.taapken.dogadoption.logic.AdvertLogic;
import beau.taapken.dogadoption.logic.EnumLogic;
import beau.taapken.dogadoption.logic.UserLogic;
import beau.taapken.dogadoption.model.Advert;
import beau.taapken.dogadoption.model.Response;
import beau.taapken.dogadoption.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:application-test.properties")
@SpringBootTest
public class UnitApplicationTests {
    // <editor-fold defaultstate="collapsed" desc="Setup">
    private final User user = new User("UUID", "Username", null);
    private final Advert advert = new Advert(1, user, "img", "title", "description", DogBreed.BEAGLE, 2);

    @Autowired
    private UserLogic userLogic;

    @Autowired
    private AdvertLogic advertLogic;

    @Autowired
    private EnumLogic enumLogic;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="UserLogic tests">
    @Test
    @DirtiesContext
    public void addUserCorrectly(){
        Response result = userLogic.addUser(user);

        String expected = "Done: Everything went correctly";

        Assert.assertEquals(expected, result.getResponseCode() + ": " + result.getResponseDescription());
    }

    @Test
    @DirtiesContext
    public void addUserIncorrectly(){
        Response result = userLogic.addUser(new User());

        String expected = "Error: org.springframework.dao.InvalidDataAccessApiUsageException: The given id must not be null!; nested exception is java.lang.IllegalArgumentException: The given id must not be null!";

        Assert.assertEquals(expected, result.getResponseCode() + ": " + result.getResponseDescription());
    }

    @Test
    @DirtiesContext
    public void addExistingUser(){
        userLogic.addUser(user);
        Response result = userLogic.addUser(user);

        String expected = "Existing: User already exists";

        Assert.assertEquals(expected, result.getResponseCode() + ": " + result.getResponseDescription());
    }

    @Test
    @DirtiesContext
    public void getExistingUsername(){
        userLogic.addUser(user);
        String result = userLogic.getUsername(user.getUUID());

        String expected = "Username";

        Assert.assertEquals(expected, result);
    }

    @Test
    @DirtiesContext
    public void getNonExistingUsername(){
        String result = userLogic.getUsername("UUID");

        String expected = null;

        Assert.assertEquals(expected, result);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="AdvertLogic tests">
    //TODO don't know how to test this right now because of OneToMany
//    @Test
//    @DirtiesContext
//    public void addAdvertCorrectly(){
//        Response result = advertLogic.addAdvert(advert);
//
//        String expected = "{\"breeds\":[\"BULLDOG\",\"GERMAN_SHEPPARD\",\"LABRADOR\",\"GOLDEN_RETRIEVER\",\"BEAGLE\",\"OTHER\"]}";
//
//        Assert.assertEquals(expected, result.getResponseCode() + ": " + result.getResponseDescription());
//    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="EnumLogic tests">
    @Test
    @DirtiesContext
    public void getDogBreeds(){
        String result = enumLogic.getDogBreeds();

        String expected = "{\"breeds\":[\"BULLDOG\",\"GERMAN_SHEPPARD\",\"LABRADOR\",\"GOLDEN_RETRIEVER\",\"BEAGLE\",\"OTHER\"]}";

        Assert.assertEquals(expected, result);
    }
    // </editor-fold>
}
