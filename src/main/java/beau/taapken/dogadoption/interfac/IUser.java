package beau.taapken.dogadoption.interfac;

import beau.taapken.dogadoption.model.Response;
import beau.taapken.dogadoption.model.User;

public interface IUser {
    Response addUser(User user);
    String getUsername(String UUID);
}
