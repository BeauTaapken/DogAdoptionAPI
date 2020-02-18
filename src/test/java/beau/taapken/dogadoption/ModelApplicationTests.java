package beau.taapken.dogadoption;

import beau.taapken.dogadoption.enumerator.ResponseCode;
import beau.taapken.dogadoption.model.Response;
import beau.taapken.dogadoption.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(locations="classpath:application-test.properties")
@SpringBootTest
public class ModelApplicationTests {
    private User user = new User("UUID", "Username");
    private Response response = new Response(ResponseCode.Done, "test");

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
}
