package FinalHikeFinder.FinalHikeFinder.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import FinalHikeFinder.FinalHikeFinder.model.Hike;
import FinalHikeFinder.FinalHikeFinder.model.HikeSpot;
import FinalHikeFinder.FinalHikeFinder.repo.UserRepo;
import FinalHikeFinder.FinalHikeFinder.service.HikeService;
import FinalHikeFinder.FinalHikeFinder.service.HikeSpotService;
import FinalHikeFinder.FinalHikeFinder.service.TeleLogicService;


@RestController
@RequestMapping("api/test")
public class TestController {
    
    @Autowired HikeSpotService hikeSpotService;
    @Autowired HikeService hikeService;
    @Autowired TeleLogicService teleLogicService;

    @Autowired UserRepo userRepo;

    @GetMapping("")
    public ResponseEntity<List<Hike>> testcontrol(){
        List<HikeSpot> hikeSpotlist = hikeSpotService.getHikeSpotList();
        List<Hike> hikeList = hikeService.getHikeList();
        return ResponseEntity.ok().body(hikeList);
    }

    @GetMapping("/user")
    public ResponseEntity<Boolean> testuser(){
        //User user = userRepo.getUser("dharmesh");
        return ResponseEntity.ok().body(userRepo.checkUserExists("rithi"));
    }

    @GetMapping("/hikes")
    public ResponseEntity<List<Hike>> gethikes(){
        List<Hike> hikeList = hikeService.getHikeList();
        return ResponseEntity.ok().body(hikeList);
    }

    @GetMapping("/hike")
    public ResponseEntity<Hike> gethike(){
        Hike hike = hikeService.getHike(1);
        return ResponseEntity.ok().body(hike);
    }

    @GetMapping("/inserthike")
    public ResponseEntity<Hike> inserthike(){
        //Hike hike = new Hike("jonah123", "Singapore", "BukitTimah", new Date(), new Date(), new Date());
        Hike hike = new Hike("rithi", "Singapore", "MacritchieReservoir", new Date(), new Date(), new Date());
        Hike inserted = hikeService.insertHike(hike);
        return ResponseEntity.ok().body(inserted);
    }

    @GetMapping("/deletehike")
    public ResponseEntity<Boolean> deletehike(){
        hikeService.deleteHike(3);
        return ResponseEntity.ok().body(true);
    }

    @GetMapping("/redischeck")
    public ResponseEntity<Boolean> redisCheck(){
        teleLogicService.setBotState("1234", 2);
        return ResponseEntity.ok().body(true);
    }

    @GetMapping("/genUUID")
    public ResponseEntity<String> genUUID(){
        String  uuid = teleLogicService.getUniqueIdentifier("jonah123");
        return ResponseEntity.ok().body(uuid);
    }
}
