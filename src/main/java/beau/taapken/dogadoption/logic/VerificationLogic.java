package beau.taapken.dogadoption.logic;

import beau.taapken.dogadoption.model.Response;
import com.google.firebase.auth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationLogic {
    private UserLogic userLogic;

    @Autowired
    public VerificationLogic(UserLogic userLogic){ this.userLogic = userLogic; }

    public boolean isUser(String idToken) throws FirebaseAuthException {
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
