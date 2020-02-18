package beau.taapken.dogadoption;

import beau.taapken.dogadoption.logic.UserLogic;
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
    private final User user = new User("UUID", "Username");

    @Autowired
    private UserLogic userLogic;

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
}
