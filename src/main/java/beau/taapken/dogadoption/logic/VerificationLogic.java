package beau.taapken.dogadoption.logic;

import com.google.firebase.auth.*;
import org.springframework.stereotype.Service;

@Service
public class VerificationLogic {
    public boolean isUser(String idToken) {
        try{
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            decodedToken.getUid();
            return true;
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        return false;
    }

}
