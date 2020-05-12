package beau.taapken.dogadoption.logic;

import com.google.firebase.auth.*;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class VerificationLogic {
    public String GetUUID(String idToken) {
        try{
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            return decodedToken.getUid();
        }
        catch (Exception ex) {
            log.error(ex);
        }
        return null;
    }
}
