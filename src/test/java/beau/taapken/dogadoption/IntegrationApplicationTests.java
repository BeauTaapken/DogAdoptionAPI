package beau.taapken.dogadoption;

import beau.taapken.dogadoption.enumerator.DogBreed;
import beau.taapken.dogadoption.logic.UserLogic;
import beau.taapken.dogadoption.model.Advert;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
public class IntegrationApplicationTests {
    // <editor-fold defaultstate="collapsed" desc="Setup">
    private final User user = new User("VtJbQmK1hogZLcAqXkhrnv6vs4n1", "Username", null);
    private final Advert advert = new Advert(1, user, "img", "title", "description", DogBreed.BEAGLE, 2);

    private final Gson gson = new Gson();

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="UserController">
    @Test
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
    public void getNonExistingUsername() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/user/getuser/fakeUUID")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)).andReturn();

        String expectedResult = "";

        Assert.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="AdvertController">
    //TODO don't know how to test this right now because of OneToMany
//    @Test
//    @DirtiesContext
//    public void addAdvertCorrectly() throws Exception {
//        System.out.println(gson.toJson(advert));
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/advert/addadvert")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(gson.toJson(advert))
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().is(200)).andReturn();
//
//        String expectedResult = "{\"responseCode\":\"Done\",\"responseDescription\":\"Everything went correctly\"}";
//
//        Assert.assertEquals(expectedResult, result.getResponse().getContentAsString());
//    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="EnumController">
    @Test
    public void getDogBreeds() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/enum/getdogbreeds")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)).andReturn();

        String expectedResult = "{\"breeds\":[\"BULLDOG\",\"GERMAN_SHEPPARD\",\"LABRADOR\",\"GOLDEN_RETRIEVER\",\"BEAGLE\",\"OTHER\"]}";

        Assert.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }
    // </editor-fold>
}
