package beau.taapken.dogadoption.logic;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;

@Configuration
@Log4j2
public class FirebaseSetup implements CommandLineRunner {
    public void run(String... args) {
        try{
            initializeFirebase();
        } catch(Exception ex) {
            log.error(ex);
        }
    }

    private void initializeFirebase() throws IOException {
        String baseURL = System.getProperty("user.dir");
        FileInputStream serviceAccount = new FileInputStream(ResourceUtils.getFile(baseURL + "/dogadoption-firebase.json"));
        FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).build();
        FirebaseApp.initializeApp(options);
    }
}
