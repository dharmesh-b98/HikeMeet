package FinalHikeFinder.FinalHikeFinder.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import FinalHikeFinder.FinalHikeFinder.model.HikeSpot;
import FinalHikeFinder.FinalHikeFinder.service.HikeSpotService;

@RestController
@RequestMapping("api/hikespots")
public class HikeSpotsController {
    
    @Autowired HikeSpotService hikeSpotService;

    @GetMapping("")
    public ResponseEntity<List<HikeSpot>> getHikeSpotList(@RequestParam("filterBy") String filterBy){
        List<HikeSpot> hikeSpotList = hikeSpotService.getFilteredHikeSpotList(filterBy);

        return ResponseEntity.ok().body(hikeSpotList);
    }

    @GetMapping("/getHikeSpot/{hikeSpotId}")
    public ResponseEntity<HikeSpot> getHikeSpot(@PathVariable("hikeSpotId") Integer hikeSpotId){
        HikeSpot hikeSpot = hikeSpotService.getHikeSpot(hikeSpotId);

        return ResponseEntity.ok().body(hikeSpot);
    }
}
