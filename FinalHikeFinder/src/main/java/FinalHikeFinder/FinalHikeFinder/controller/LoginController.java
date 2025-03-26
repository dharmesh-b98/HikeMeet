package FinalHikeFinder.FinalHikeFinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import FinalHikeFinder.FinalHikeFinder.model.User;
import FinalHikeFinder.FinalHikeFinder.service.UserService;
import jakarta.json.Json;
import jakarta.json.JsonObject;

@RestController
@RequestMapping("api/login")
public class LoginController {
    
    @Autowired UserService userService;

    
    //login
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> checkLogin(@RequestBody User user){
        Boolean isCredentialsCorrect = userService.checkLoginCredentials(user.getUsername(), user.getPassword());
        return ResponseEntity.ok().body(isCredentialsCorrect);
    }


    //register
    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@RequestBody User user){
        
        if (userService.checkUserExists(user.getUsername())){
            JsonObject response = Json.createObjectBuilder().add("reply", "Username already exists").build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
        }

        else{
            user.setRole("USER");
            Boolean userInserted = userService.insertNewUser(user);
            JsonObject response = Json.createObjectBuilder().add("reply", "New user added").build();
            return ResponseEntity.status(HttpStatus.OK).body(response.toString());
        }
    }
}
