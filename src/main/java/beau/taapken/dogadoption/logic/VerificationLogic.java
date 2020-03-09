package beau.taapken.dogadoption.logic;

import com.google.firebase.auth.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationLogic {
    private UserLogic userLogic;

    @Autowired
    public VerificationLogic(UserLogic userLogic){ this.userLogic = userLogic; }

    public boolean isUser(String idToken) throws FirebaseAuthException {
        FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
        String uid = decodedToken.getUid();
        return userLogic.hasUUID(uid);
    }

}
