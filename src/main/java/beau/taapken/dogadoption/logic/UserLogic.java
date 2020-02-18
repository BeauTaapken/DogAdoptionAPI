package beau.taapken.dogadoption.logic;

import beau.taapken.dogadoption.enumerator.ResponseCode;
import beau.taapken.dogadoption.interfac.IUser;
import beau.taapken.dogadoption.model.Response;
import beau.taapken.dogadoption.model.User;
import beau.taapken.dogadoption.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLogic implements IUser {
    private UserRepository userRepository;

    @Autowired
    public UserLogic(UserRepository userRepository) { this.userRepository = userRepository; }

    public Response addUser(User user) {
        Response response = new Response(ResponseCode.Error, "Everything is broken here.");
        try{
            if(userRepository.existsById(user.getUUID())){
                response.setResponseCode(ResponseCode.Existing);
                response.setResponseDescription("User already exists");
            }
            else{
                userRepository.save(user);
                response.setResponseCode(ResponseCode.Done);
                response.setResponseDescription("Everything went correctly");
            }
        }
        catch(Exception ex){
            response.setResponseCode(ResponseCode.Error);
            response.setResponseDescription(ex.toString());
        }
        return response;
    }

    public String getUsername(String UUID) {
        try{
            System.out.println(UUID);
            return userRepository.getOne(UUID).getUsername();
        }
        catch(Exception ex){
            System.out.println(ex);
        }
        return null;
    }
}
