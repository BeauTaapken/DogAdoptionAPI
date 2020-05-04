package beau.taapken.dogadoption.logic;

import com.google.firebase.auth.*;
import org.springframework.stereotype.Service;

@Service
public class VerificationLogic {
    public String GetUUID(String idToken) {
        try{
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            return decodedToken.getUid();
        }
        catch (Exception ex) {
            System.out.println(ex);
        }
        return null;
    }
}
