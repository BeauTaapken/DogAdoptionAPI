package beau.taapken.dogadoption.controller;

import beau.taapken.dogadoption.enumerator.ResponseCode;
import beau.taapken.dogadoption.interfac.IUser;
import beau.taapken.dogadoption.logic.UserLogic;
import beau.taapken.dogadoption.logic.VerificationLogic;
import beau.taapken.dogadoption.model.Response;
import beau.taapken.dogadoption.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin
@RequestMapping("/user")
@RestController
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
