package beau.taapken.dogadoption;

import beau.taapken.dogadoption.logic.FirebaseSetup;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class DogadoptionApplication {

    public static void main(String[] args) {
        SpringApplication.run(DogadoptionApplication.class, args);
    }
}
