package main.sk.tuke.gamestudio.service;

public interface UserService {

    int doesUsernameExist(String username);
    boolean register(String username, String password);
    boolean login(String username, String password);
}