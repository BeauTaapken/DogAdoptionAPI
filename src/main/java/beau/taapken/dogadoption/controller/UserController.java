package beau.taapken.dogadoption.controller;

import beau.taapken.dogadoption.enumerator.ResponseCode;
import beau.taapken.dogadoption.interfac.IUser;
import beau.taapken.dogadoption.logic.UserLogic;
import beau.taapken.dogadoption.model.Response;
import beau.taapken.dogadoption.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
public class UserController implements IUser {
    @Autowired
    private UserLogic userLogic;

    @PostMapping(path = "/adduser")
    public Response addUser(@RequestBody User user) {
        try{
            return userLogic.addUser(user);
        }
        catch (Exception ex){
            return new Response(ResponseCode.Error, ex.toString());
        }
    }
}
