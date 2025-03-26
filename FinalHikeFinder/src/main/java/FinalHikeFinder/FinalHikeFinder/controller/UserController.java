package FinalHikeFinder.FinalHikeFinder.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import FinalHikeFinder.FinalHikeFinder.model.User;
import FinalHikeFinder.FinalHikeFinder.service.TeleLogicService;
import FinalHikeFinder.FinalHikeFinder.service.UserService;
import jakarta.json.Json;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired UserService userService;
    @Autowired TeleLogicService teleLogicService;

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username){
        User user = userService.getUser(username);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/getUUID/{username}")
    public ResponseEntity<String> getUUID(@PathVariable("username") String username){
        String  uuid = teleLogicService.getUniqueIdentifier(username);
        return ResponseEntity.ok().body(Json.createObjectBuilder().add("uuid", uuid).build().toString());
    }
}
