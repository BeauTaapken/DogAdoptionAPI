package beau.taapken.dogadoption;

import beau.taapken.dogadoption.enumerator.DogBreed;
import beau.taapken.dogadoption.enumerator.ResponseCode;
import beau.taapken.dogadoption.model.Advert;
import beau.taapken.dogadoption.model.Response;
import beau.taapken.dogadoption.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(locations="classpath:application-test.properties")
@SpringBootTest
public class ModelApplicationTests {
    // <editor-fold defaultstate="collapsed" desc="Setup">
    private User user = new User("UUID", "Username", null);
    private Response response = new Response(ResponseCode.Done, "test");
    private final Advert advert = new Advert(1, user, "title", "description", DogBreed.BEAGLE, 2);
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

    // <editor-fold defaultstate="collapsed" desc="Response model tests">
    @Test
    public void getResponseCode() {
        ResponseCode result = response.getResponseCode();
        ResponseCode expected = ResponseCode.Done;
        Assert.assertEquals(expected, result);
    }

    @Test
    public void setResponseCode() {
        response.setResponseCode(ResponseCode.Existing);
        ResponseCode result = response.getResponseCode();
        ResponseCode expected = ResponseCode.Existing;
        Assert.assertEquals(expected, result);
    }

    @Test
    public void getResponseDescription() {
        String result = response.getResponseDescription();
        String expected = "test";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void setResponseDescription() {
        response.setResponseDescription("newTest");
        String result = response.getResponseDescription();
        String expected = "newTest";
        Assert.assertEquals(expected, result);
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Advert model tests">
    @Test
    public void getAdvertId() {
        int result = advert.getAdvertId();
        int expected = 1;
        Assert.assertEquals(expected, result);
    }

    @Test
    public void setAdvertId() {
        advert.setAdvertId(2);
        int result = advert.getAdvertId();
        int expected = 2;
        Assert.assertEquals(expected, result);
    }

    @Test
    public void getUserUUID() {
        //getUUID is called 2 times because advert.getUUID gives a user, which can't be tested and the UUID of the user can
        String result = advert.getUUID().getUUID();
        String expected = "UUID";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void setUserUUID() {
        advert.setUUID(new User("newUUID", "Username", null));
        String result = advert.getUUID().getUUID();
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
    public void setTitle() {
        advert.setTitle("newTitle");
        String result = advert.getTitle();
        String expected = "newTitle";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void getDescription() {
        String result = advert.getDescription();
        String expected = "description";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void setDescription() {
        advert.setDescription("newDescription");
        String result = advert.getDescription();
        String expected = "newDescription";
        Assert.assertEquals(expected, result);
    }

    @Test
    public void getDogBreed() {
        DogBreed result = advert.getBreed();
        DogBreed expected = DogBreed.BEAGLE;
        Assert.assertEquals(expected, result);
    }

    @Test
    public void setDogBreed() {
        advert.setBreed(DogBreed.BULLDOG);
        DogBreed result = advert.getBreed();
        DogBreed expected = DogBreed.BULLDOG;
        Assert.assertEquals(expected, result);
    }

    @Test
    public void getAge() {
        int result = advert.getAge();
        int expected = 2;
        Assert.assertEquals(expected, result);
    }

    @Test
    public void setAge() {
        advert.setAge(1);
        int result = advert.getAge();
        int expected = 1;
        Assert.assertEquals(expected, result);
    }
    // </editor-fold>
}
