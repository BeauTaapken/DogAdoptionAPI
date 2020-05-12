package beau.taapken.dogadoption.controller;

import beau.taapken.dogadoption.enumerator.ResponseCode;
import beau.taapken.dogadoption.interfaces.IUser;
import beau.taapken.dogadoption.logic.UserLogic;
import beau.taapken.dogadoption.logic.VerificationLogic;
import beau.taapken.dogadoption.model.Response;
import beau.taapken.dogadoption.model.User;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("/user")
@RestController
@Log4j2
public class UserController implements IUser {
    @Autowired
    private UserLogic userLogic;
    @Autowired
    private VerificationLogic verificationLogic;

    @PostMapping("/adduser")
    public Response addUser(@RequestBody User user) {
        try{
            return userLogic.addUser(user);
        }
        catch (Exception ex){
            log.error(ex);
            return new Response(ResponseCode.Error, ex.toString());
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
