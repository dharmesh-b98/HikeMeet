package FinalHikeFinder.FinalHikeFinder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import FinalHikeFinder.FinalHikeFinder.model.Hike;
import FinalHikeFinder.FinalHikeFinder.service.HikeService;


@RestController
@RequestMapping("/api/hikes")
public class HikeController {
    
    @Autowired HikeService hikeService;

    @GetMapping("/getHikes")
    public ResponseEntity<List<Hike>> getHikeList(){
        List<Hike> hikeList = hikeService.getHikeList();
        
        return ResponseEntity.ok().body(hikeList);
    }


    @PostMapping("/postHike")
    public ResponseEntity<Hike> postHike(@RequestBody Hike hike){
        Hike hikeInserted = hikeService.insertHike(hike);
        return ResponseEntity.ok().body(hikeInserted);
    }


    @GetMapping("/getHike/{hostedhike_id}")
    public ResponseEntity<Hike> getHike(@PathVariable("hostedhike_id") Integer hostedhike_id){
        Hike hike = hikeService.getHike(hostedhike_id);
        return ResponseEntity.ok().body(hike);
    }


    @DeleteMapping("/deleteHike/{hostedhike_id}")
    public ResponseEntity<Boolean> deleteHike(@PathVariable("hostedhike_id") Integer hostedhike_id){
        hikeService.deleteHike(hostedhike_id);
        return ResponseEntity.ok().body(true);
    }


    @GetMapping("/joinHike/{hostedhike_id}/{username}")
    public ResponseEntity<Hike> joinHike(@PathVariable("hostedhike_id") Integer hostedhike_id, @PathVariable("username") String username){
        Hike hikeJoined = hikeService.joinHike(hostedhike_id, username);
        return ResponseEntity.ok().body(hikeJoined);
    }


    @GetMapping("/unjoinHike/{hostedhike_id}/{username}")
    public ResponseEntity<Hike> unjoinHike(@PathVariable("hostedhike_id") Integer hostedhike_id, @PathVariable("username") String username){
        Hike hikeUnjoined = hikeService.unjoinHike(hostedhike_id, username);
        return ResponseEntity.ok().body(hikeUnjoined);
    }

    

    
}
