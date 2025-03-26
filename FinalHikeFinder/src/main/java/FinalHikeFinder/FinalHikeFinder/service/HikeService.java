package FinalHikeFinder.FinalHikeFinder.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import FinalHikeFinder.FinalHikeFinder.model.Hike;
import FinalHikeFinder.FinalHikeFinder.repo.HikeRepo;

@Service
public class HikeService {
    
    @Autowired HikeRepo hikeRepo;


    //join hike
    public Hike joinHike(Integer hostedhike_id, String username){
        return hikeRepo.joinHike(hostedhike_id, username);
    }


    //unjoin hike
    public Hike unjoinHike(Integer hostedhike_id, String username){
        return hikeRepo.unjoinHike(hostedhike_id, username);
    }


    //delete a hike
    public void deleteHike(Integer hostedhike_id){
        hikeRepo.deleteHike(hostedhike_id);
    }


    //filtering hikelist by username
    public List<Hike> getPersonalHostedHikeList(String username){
        List<Hike> hikeList = hikeRepo.getHikeList();
        List<Hike> personalHostedHikeList = hikeList.stream().filter(hike -> hike.getHost().equals(username)).toList();
        return personalHostedHikeList;
    }


    //filtering hikelist by country
    public List<Hike> getFilteredHikeList(String filterBy){
        List<Hike> hikeList = hikeRepo.getHikeList();
        List<Hike> filteredHikeList = new ArrayList<>();
        if (filterBy.equals("Japan") || filterBy.equals("Singapore") || filterBy.equals("India")){
            filteredHikeList = hikeList.stream().filter(hike -> hike.getCountry().equals(filterBy)).toList();
            System.out.println(filteredHikeList.toString());
        }
        else{
            filteredHikeList = hikeList.stream().toList();
        }
        return filteredHikeList;        
    }


    //get hike
    public Hike getHike(Integer hostedhike_id){
        return hikeRepo.getHike(hostedhike_id);
    }


    //insert hike
    @Transactional
    public Hike insertHike(Hike hike){
        return hikeRepo.insertHike(hike);
    }


    //gethikelist
    public List<Hike> getHikeList(){
        return hikeRepo.getHikeList();
    }


    //gethikesjoined
    public List<Hike> getHikesJoined(String username){
        return hikeRepo.getHikesJoined(username);
    }

    //convert hikelist to string
    public String convertHikeListToString(List<Hike> hikeList){
        return hikeRepo.convertHikeListToString(hikeList);
    }
}
