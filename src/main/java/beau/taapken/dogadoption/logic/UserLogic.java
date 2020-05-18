package beau.taapken.dogadoption.logic;

import beau.taapken.dogadoption.enumerator.ResponseCode;
import beau.taapken.dogadoption.model.Response;
import beau.taapken.dogadoption.model.User;
import beau.taapken.dogadoption.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserLogic {
    private UserRepository userRepository;

    @Autowired
    public UserLogic(UserRepository userRepository) { this.userRepository = userRepository; }

    public ResponseEntity addUser(User user) {
        Response response = new Response(ResponseCode.Error, "Everything is broken here.");
        try{
            if(userRepository.existsById(user.getUUID())){
                return new ResponseEntity<>("User already exists with given id", HttpStatus.FORBIDDEN);
            }
            else{
                userRepository.save(user);
                return new ResponseEntity<>("Everything went correctly", HttpStatus.OK);
            }
        }
        catch(Exception ex){
            log.error(ex);
            return new ResponseEntity<>(ex.toString(), HttpStatus.REQUEST_TIMEOUT);
        }
    }

    public String getUsername(String UUID) {
        try{
            return userRepository.getOne(UUID).getUsername();
        }
        catch(Exception ex){
            log.error(ex);
        }
        return null;
    }
}
