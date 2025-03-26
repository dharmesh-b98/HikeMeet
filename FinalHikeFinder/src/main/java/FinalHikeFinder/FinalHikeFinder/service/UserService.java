package FinalHikeFinder.FinalHikeFinder.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import FinalHikeFinder.FinalHikeFinder.model.User;
import FinalHikeFinder.FinalHikeFinder.repo.UserRepo;

@Service
public class UserService {
    
    @Autowired UserRepo userRepo;

    @Value("${admin.username}") private String adminUsername;
    @Value("${admin.username}") private String adminPassword;


    //update telegram info
    public Boolean updateTelegramInfo(String telegramUsername, String chatId, String username){
        return userRepo.updateTelegramInfo(telegramUsername, chatId, username);
    }


    //insert new user
    public Boolean insertNewUser(User user){
        return userRepo.insertNewUser(user);
    }


    //get user
    public User getUser(String username){
        return userRepo.getUser(username);
    }


    //checks if username exists and checks if password correct
    public Boolean checkLoginCredentials(String username, String password){
        if (checkUserExists(username)){
            if (checkPasswordCorrect(username, password)){
                return true;
            }
        }
        return false;
    }


    //check if username exists
    public Boolean checkUserExists(String userName){
        return userRepo.checkUserExists(userName);
    }


    //check password (helper)
    private Boolean checkPasswordCorrect(String username, String password){ //only if user exists
        if (checkUserExists(username)){
            User user = userRepo.getUser(username);
            return (password .equals(user.getPassword()));
        }
        return false;
    }



    //used in commandlinerunner
    public Boolean initialiseUsers(){
        if (!userRepo.isInitialised()){
            User admin = new User(adminUsername, adminPassword, "ADMIN", new ArrayList<>());
            userRepo.insertNewUser(admin);
            return true;
        }
        return false;
    }
}
