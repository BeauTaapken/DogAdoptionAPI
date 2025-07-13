package beau.taapken.dogadoption.controller;

import beau.taapken.dogadoption.logic.UserLogic;
import beau.taapken.dogadoption.logic.VerificationLogic;
import beau.taapken.dogadoption.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("/user")
@RestController
@Log4j2
public class UserController {
    @Autowired
    private UserLogic userLogic;
    @Autowired
    private VerificationLogic verificationLogic;

    @PostMapping("/adduser")
    public ResponseEntity addUser(@RequestBody User user) {
        try{
            return userLogic.addUser(user);
        }
        catch (Exception ex){
            log.error(ex);
            return new ResponseEntity<>(ex.toString(), HttpStatus.REQUEST_TIMEOUT);
        }
    }

    @GetMapping("/getuser/{UUID}")
    public String getUsername(@PathVariable String UUID) {
        try{
            return userLogic.getUsername(UUID);
        }
        catch(Exception ex){
            return null;
        }
    }
}
