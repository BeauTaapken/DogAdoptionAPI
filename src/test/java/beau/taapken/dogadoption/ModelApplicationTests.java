package beau.taapken.dogadoption;

import beau.taapken.dogadoption.enumerator.DogBreed;
import beau.taapken.dogadoption.enumerator.ResponseCode;
import beau.taapken.dogadoption.model.Advert;
import beau.taapken.dogadoption.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

@TestPropertySource(locations="classpath:application-test.properties")
@SpringBootTest
public class ModelApplicationTests {
    // <editor-fold defaultstate="collapsed" desc="Setup">
    private User user = new User("UUID", "Username");
    private final Advert advert = new Advert("1", user, "img" , "title", "description", DogBreed.BEAGLE, 2, 1, 1, "testplace", LocalDateTime.now());
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="User model tests">
    @Test
    public void getUUID() {
        String result = user.getUUID();
        String expected = "UUID";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void setUUID() {
        user.setUUID("newUUID");
        String result = user.getUUID();
        String expected = "newUUID";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void getUsername() {
        String result = user.getUsername();
        String expected = "Username";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void setUsername() {
        user.setUsername("newUsername");
        String result = user.getUsername();
        String expected = "newUsername";
        Assert.assertEquals(expected, result);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Advert model tests">
    @Test
    public void getAdvertId() {
        String result = advert.getAdvertId();
        String expected = "1";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void setAdvertId() {
        advert.setAdvertId("2");
        String result = advert.getAdvertId();
        String expected = "2";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void getUserUUID() {
        //getUUID is called 2 times because advert.getUUID gives a user, which can't be tested and the UUID of the user can
        String result = advert.getUser().getUUID();
        String expected = "UUID";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void setUserUUID() {
        advert.setUser(new User("newUUID", "Username"));
        String result = advert.getUser().getUUID();
        String expected = "newUUID";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void getTitle() {
        String result = advert.getTitle();
        String expected = "title";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void getDescription() {
        String result = advert.getDescription();
        String expected = "description";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void getDogBreed() {
        DogBreed result = advert.getBreed();
        DogBreed expected = DogBreed.BEAGLE;
        Assert.assertEquals(expected, result);
    }

    @Test
    public void getAge() {
        int result = advert.getAge();
        int expected = 2;
        Assert.assertEquals(expected, result);
    }
    // </editor-fold>
}
