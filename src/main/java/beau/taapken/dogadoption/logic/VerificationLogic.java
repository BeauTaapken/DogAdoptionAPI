package beau.taapken.dogadoption.logic;

import com.google.firebase.auth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationLogic {
    @Autowired
    private UserLogic userLogic;

    public boolean isUser(String idToken) throws FirebaseAuthException {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        String uid = decodedToken.getUid();
        return userLogic.hasUUID(uid);
    }

}
