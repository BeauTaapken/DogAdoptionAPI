package beau.taapken.dogadoption;

import beau.taapken.dogadoption.enumerator.DogBreed;
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

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
public class IntegrationApplicationTests {
    // <editor-fold defaultstate="collapsed" desc="Setup">
    private final User user = new User("VtJbQmK1hogZLcAqXkhrnv6vs4n1", "Beau", null);
    private final Advert advert = new Advert("1", user, "img", "title", "description", DogBreed.BEAGLE, 2, 1, 1, "testplace", LocalDateTime.now());

    private String idToken = null;

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

        String expectedResult = "Everything went correctly";

        Assert.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }

    @Test
    public void addUserIncorrectly() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/user/adduser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(new User()))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(408)).andReturn();

        String expectedResult = "org.springframework.dao.InvalidDataAccessApiUsageException: The given id must not be null!; nested exception is java.lang.IllegalArgumentException: The given id must not be null!";

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
                .andExpect(status().is(403)).andReturn();

        String expectedResult = "User already exists with given id";

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

        String expectedResult = "Beau";

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
    //TODO make a firebase test key that is always usable
//    @Test
//    public void addAdvertCorrectly() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/user/adduser")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(gson.toJson(user))
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().is(200)).andReturn();
//
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/advert/addadvert")
//                .header("id", idToken)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\n" +
//                        "\t\"UUID\": {\n" +
//                        "\t  \"UUID\": \"VtJbQmK1hogZLcAqXkhrnv6vs4n1\",\n" +
//                        "\t  \"Username\": null\n" +
//                        "\t},\n" +
//                        "\t\"img\": \"t\",\n" +
//                        "\t\"title\": \"t\",\n" +
//                        "\t\"description\": \"t\",\n" +
//                        "\t\"breed\": 1,\n" +
//                        "\t\"age\": 15\n" +
//                        "}")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().is(200)).andReturn();
//
//        String expectedResult = "{\"responseCode\":\"Done\",\"responseDescription\":\"Everything went correctly\"}";
//
//        Assert.assertEquals(expectedResult, result.getResponse().getContentAsString());
//    }


//    @Test
//    public void addAdvertIncorrectToken() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/user/adduser")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(gson.toJson(user))
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().is(200)).andReturn();
//
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/advert/addadvert")
//                .header("id", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\n" +
//                        "\t\"UUID\": {\n" +
//                        "\t  \"UUID\": \"VtJbQmK1hogZLcAqXkhrnv6vs4n1\",\n" +
//                        "\t  \"Username\": null\n" +
//                        "\t},\n" +
//                        "\t\"img\": \"t\",\n" +
//                        "\t\"title\": \"t\",\n" +
//                        "\t\"description\": \"t\",\n" +
//                        "\t\"breed\": 1,\n" +
//                        "\t\"age\": 15\n" +
//                        "}")
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().is(200)).andReturn();
//
//        String expectedResult = "{\"responseCode\":\"Error\",\"responseDescription\":\"Account could not be found. Please don't use this api if you haven't added a account.\"}";
//
//        Assert.assertEquals(expectedResult, result.getResponse().getContentAsString());
//    }
//
//    @Test
//    public void addAdvertIncorrectAdvert() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/user/adduser")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(gson.toJson(user))
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().is(200)).andReturn();
//
//        System.out.println(idToken);
//
//        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/advert/addadvert")
//                .header("id", idToken)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(gson.toJson(advert))
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().is(200)).andReturn();
//
//        String expectedResult = "{\"responseCode\":\"Error\",\"responseDescription\":\"org.springframework.transaction.TransactionSystemException: Could not commit JPA transaction; nested exception is javax.persistence.RollbackException: Error while committing the transaction\"}";
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
