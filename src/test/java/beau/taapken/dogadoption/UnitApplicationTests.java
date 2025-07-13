package beau.taapken.dogadoption;

import beau.taapken.dogadoption.enumerator.DogBreed;
import beau.taapken.dogadoption.enumerator.ResponseCode;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
public class UnitApplicationTests {
    // <editor-fold defaultstate="collapsed" desc="Setup">
    private final User user = new User("VtJbQmK1hogZLcAqXkhrnv6vs4n1", "Beau");
    private final Advert advert = new Advert("1", user, "img", "title", "description", DogBreed.BEAGLE, 2, 1, 1, "testplace", LocalDateTime.now());

    @Autowired
    private UserLogic userLogic;

    @Autowired
    private AdvertLogic advertLogic;

    @Autowired
    private EnumLogic enumLogic;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="UserLogic tests">
    @Test
    public void addUserCorrectly(){
        ResponseEntity result = userLogic.addUser(user);

        String expected = "200 OK: Everything went correctly";

        Assert.assertEquals(expected, result.getStatusCode() + ": " + result.getBody());
    }

    @Test
    public void addUserIncorrectly(){
        ResponseEntity result = userLogic.addUser(new User());

        String expected = "408 REQUEST_TIMEOUT: org.springframework.dao.InvalidDataAccessApiUsageException: The given id must not be null!; nested exception is java.lang.IllegalArgumentException: The given id must not be null!";

        Assert.assertEquals(expected, result.getStatusCode() + ": " + result.getBody());
    }

    @Test
    public void addExistingUser(){
        userLogic.addUser(user);
        ResponseEntity result = userLogic.addUser(user);

        String expected = "403 FORBIDDEN: User already exists with given id";

        Assert.assertEquals(expected, result.getStatusCode() + ": " + result.getBody());
    }

    @Test
    public void getExistingUsername(){
        userLogic.addUser(user);
        String result = userLogic.getUsername(user.getUUID());

        String expected = "Beau";

        Assert.assertEquals(expected, result);
    }

    @Test
    public void getNonExistingUsername(){
        String result = userLogic.getUsername("UUID");

        String expected = null;

        Assert.assertEquals(expected, result);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="AdvertLogic tests">
    @Test
    public void addAdvertCorrectly(){
        userLogic.addUser(user);

        ResponseEntity result = advertLogic.addAdvert(advert);

        String expected = "200 OK: Everything went correctly";

        Assert.assertEquals(expected, result.getStatusCode() + ": " + result.getBody());
    }

    @Test
    public void getAdvertsCorrectly(){
        userLogic.addUser(user);
        advertLogic.addAdvert(advert);

        List<Advert> result = advertLogic.getAdverts(0, 1);

        int expected = 1;

        Assert.assertEquals(expected, result.size());
    }

    @Test
    public void getAdvertsIncorrectly(){
        userLogic.addUser(user);
        advertLogic.addAdvert(advert);

        List<Advert> result = advertLogic.getAdverts(5, 1);

        int expected = 0;

        Assert.assertEquals(expected, result.size());
    }

    @Test
    public void deleteExistingAdvert(){
        userLogic.addUser(user);
        advertLogic.addAdvert(advert);

        //Need to get just added advert, id set in original advert is overwritten with random UUID
        List<Advert> advert = advertLogic.getAdverts(0, 1);

        ResponseEntity result = advertLogic.deleteAdvert(advert.get(0).getAdvertId());

        String expected = "200 OK: Everything went correctly";

        Assert.assertEquals(expected, result.getStatusCode() + ": " + result.getBody());
    }

    @Test
    public void deleteNonExistingAdvert(){
        userLogic.addUser(user);
        advertLogic.addAdvert(advert);

        ResponseEntity result = advertLogic.deleteAdvert("50");

        String expected = "408 REQUEST_TIMEOUT: org.springframework.dao.EmptyResultDataAccessException: No class beau.taapken.dogadoption.model.Advert entity with id 50 exists!";

        Assert.assertEquals(expected, result.getStatusCode() + ": " + result.getBody());
    }

    @Test
    public void updateExistingAdvert(){
        userLogic.addUser(user);
        advertLogic.addAdvert(advert);

        List<Advert> oldAdverts = advertLogic.getAdverts(0, 1);

        Advert ad = advert;
        ad.setAdvertId(oldAdverts.get(0).getAdvertId());
        ad.setDescription("new description");

        advertLogic.updateAdvert(ad);

        List<Advert> adverts = advertLogic.getAdverts(0, 1);

        String expected = "new description";

        //This is a better test than just testing if the code has been executed, now test should also look if item has been updated like expected
        Assert.assertEquals(expected, adverts.get(0).getDescription());
    }

    @Test
    public void updateNonExistingAdvert(){
        userLogic.addUser(user);
        advertLogic.addAdvert(advert);

        Advert ad = advert;
        ad.setDescription("new description");

        advertLogic.updateAdvert(ad);

        List<Advert> adverts = advertLogic.getAdverts(0, 1);

        String expected = "description";

        //This is a better test than just testing if the code has been executed, now test should also look if item has been updated like expected
        Assert.assertEquals(expected, adverts.get(0).getDescription());
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="EnumLogic tests">
    @Test
    public void getDogBreeds(){
        String result = enumLogic.getDogBreeds();

        String expected = "{\"breeds\":[\"BULLDOG\",\"GERMAN_SHEPPARD\",\"LABRADOR\",\"GOLDEN_RETRIEVER\",\"BEAGLE\",\"OTHER\"]}";

        Assert.assertEquals(expected, result);
    }
    // </editor-fold>
}
