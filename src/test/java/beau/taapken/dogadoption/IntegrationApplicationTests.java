package beau.taapken.dogadoption;

import beau.taapken.dogadoption.logic.UserLogic;
import beau.taapken.dogadoption.model.User;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:application-test.properties")
@SpringBootTest
public class IntegrationApplicationTests {
    private final User user = new User("UUID", "Username");

    private final Gson gson = new Gson();

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    @DirtiesContext
    public void addUserCorrectly() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/adduser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)).andReturn();

        String expectedResult = "{\"responseCode\":\"Done\",\"responseDescription\":\"Everything went correctly\"}";

        Assert.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }

    @Test
    @DirtiesContext
    public void addUserIncorrectly() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/adduser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(new User()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)).andReturn();

        String expectedResult = "{\"responseCode\":\"Error\",\"responseDescription\":\"org.springframework.dao.InvalidDataAccessApiUsageException: The given id must not be null!; nested exception is java.lang.IllegalArgumentException: The given id must not be null!\"}";

        Assert.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }

    @Test
    @DirtiesContext
    public void addExistingUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/adduser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/adduser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)).andReturn();

        String expectedResult = "{\"responseCode\":\"Existing\",\"responseDescription\":\"User already exists\"}";

        Assert.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }

    //TODO add a test where the database gets dropped?

    @Test
    @DirtiesContext
    public void getExistingUsername() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/adduser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/getuser/" + user.getUUID())
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)).andReturn();

        String expectedResult = "Username";

        Assert.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }

    @Test
    @DirtiesContext
    public void getNonExistingUsername() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/getuser/fakeUUID")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)).andReturn();

        String expectedResult = "";

        Assert.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }
}
