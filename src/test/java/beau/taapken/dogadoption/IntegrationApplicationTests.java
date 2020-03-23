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
import org.mockito.Mockito;
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

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@TestPropertySource(locations="classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest
public class IntegrationApplicationTests {
    // <editor-fold defaultstate="collapsed" desc="Setup">
    private final User user = new User("VtJbQmK1hogZLcAqXkhrnv6vs4n1", "Beau");
    private final Advert advert = new Advert(1, user, "img", "title", "description", DogBreed.BEAGLE, 2);

    //TODO look into getting a token that doesn't expire
    private final String idToken = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjhjZjBjNjQyZDQwOWRlODJlY2M5MjI4ZTRiZDc5OTkzOTZiNTY3NDAiLCJ0eXAiOiJKV1QifQ.eyJuYW1lIjoiQmVhdSIsImlzcyI6Imh0dHBzOi8vc2VjdXJldG9rZW4uZ29vZ2xlLmNvbS9kb2dhZG9wdGlvbi1hYjI4NiIsImF1ZCI6ImRvZ2Fkb3B0aW9uLWFiMjg2IiwiYXV0aF90aW1lIjoxNTgzODMxMjQxLCJ1c2VyX2lkIjoiVnRKYlFtSzFob2daTGNBcVhraHJudjZ2czRuMSIsInN1YiI6IlZ0SmJRbUsxaG9nWkxjQXFYa2hybnY2dnM0bjEiLCJpYXQiOjE1ODM4MzEyNDUsImV4cCI6MTU4MzgzNDg0NSwiZW1haWwiOiJiZWF1QGxpb25jb2RlLm5sIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJmaXJlYmFzZSI6eyJpZGVudGl0aWVzIjp7ImVtYWlsIjpbImJlYXVAbGlvbmNvZGUubmwiXX0sInNpZ25faW5fcHJvdmlkZXIiOiJwYXNzd29yZCJ9fQ.DRZ7dfKoHvxqGhS8aslzyOf8-nDZQwrhjdoI82xqu0rppmfhVDy3IWGPwe-hHnYv0LLeCaWg-8jAUrh4p54dEIKw9i6rLhwXy5_21FWfBQDZHFAeT9N6p9xg_mQGD92NKaeP1MlFJkWOzTtgqxK9_FNjOp9Bry41EZU04ClStA4xfU2PLKZesqSwAKVd0v-0BhFWsNG0kSR9iMndKR2pQn6vb0zXZgo8IaNeOtVXekwFaPvtZQLl_Wb0P2AONrlF8X-j1iVUUe9r0Hvpo9IrnbM8hD2IM6mCPwMOOEbMzqkOZJzwHiOrNj3cVV5s5Z-amG7KB73l7vX9Csmp5HK2LQ";

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
    @Test
    public void addAdvertCorrectly() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/adduser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)).andReturn();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/advert/addadvert")
                .header("id", idToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"UUID\": {\n" +
                        "\t  \"UUID\": \"VtJbQmK1hogZLcAqXkhrnv6vs4n1\",\n" +
                        "\t  \"Username\": null\n" +
                        "\t},\n" +
                        "\t\"img\": \"t\",\n" +
                        "\t\"title\": \"t\",\n" +
                        "\t\"description\": \"t\",\n" +
                        "\t\"breed\": 1,\n" +
                        "\t\"age\": 15\n" +
                        "}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)).andReturn();

        String expectedResult = "{\"responseCode\":\"Done\",\"responseDescription\":\"Everything went correctly\"}";

        Assert.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }

    @Test
    public void addAdvertIncorrectToken() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/adduser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)).andReturn();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/advert/addadvert")
                .header("id", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\t\"UUID\": {\n" +
                        "\t  \"UUID\": \"VtJbQmK1hogZLcAqXkhrnv6vs4n1\",\n" +
                        "\t  \"Username\": null\n" +
                        "\t},\n" +
                        "\t\"img\": \"t\",\n" +
                        "\t\"title\": \"t\",\n" +
                        "\t\"description\": \"t\",\n" +
                        "\t\"breed\": 1,\n" +
                        "\t\"age\": 15\n" +
                        "}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)).andReturn();

        String expectedResult = "{\"responseCode\":\"Error\",\"responseDescription\":\"Account could not be found. Please don't use this api if you haven't added a account.\"}";

        Assert.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }

    @Test
    public void addAdvertIncorrectAdvert() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/adduser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(user))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)).andReturn();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/advert/addadvert")
                .header("id", idToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(advert))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)).andReturn();

        String expectedResult = "{\"responseCode\":\"Error\",\"responseDescription\":\"org.springframework.transaction.TransactionSystemException: Could not commit JPA transaction; nested exception is javax.persistence.RollbackException: Error while committing the transaction\"}";

        Assert.assertEquals(expectedResult, result.getResponse().getContentAsString());
    }
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
