package FinalHikeFinder.FinalHikeFinder.repo;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import FinalHikeFinder.FinalHikeFinder.model.Hike;
import FinalHikeFinder.FinalHikeFinder.repo.constants.SQL_Queries;

@Repository
public class HikeRepo {
    
    @Autowired JdbcTemplate template;


    //join hike
    public Hike joinHike(Integer hostedhike_id, String username) {
        Integer rowsUpdated = template.update(SQL_Queries.joinHike_SQL, hostedhike_id, username);
        return getHike(hostedhike_id);
    }


    //unjoin hike
    public Hike unjoinHike(Integer hostedhike_id, String username) {
        Integer rowsUpdated = template.update(SQL_Queries.unjoinHike_SQL, hostedhike_id, username);
        return getHike(hostedhike_id);
    }


    //delete hike
    public void deleteHike(Integer hostedhike_id){
        template.update(SQL_Queries.deleteHike_SQL, hostedhike_id);
    }


    //get one hike
    public Hike getHike(Integer hostedhike_id){
        SqlRowSet rs = template.queryForRowSet(SQL_Queries.getHike_SQL, hostedhike_id);
        rs.next();
        Integer id = rs.getInt("hostedhike_id");
        String host = rs.getString("hostname");
        String country = rs.getString("country");
        String hikeSpotName = rs.getString("hikespotname");
        Date dateAndTime = new Date(Long.parseLong(rs.getString("dateandtime")));
        Date sunriseTime = new Date (Long.parseLong(rs.getString("sunrisetime")));
        Date sunsetTime = new Date(Long.parseLong(rs.getString("sunsettime")));
        
        List<String> usersJoined = new ArrayList<>();
        if (!(rs.getString("usersjoined") == null)){
            String usersJoinedString = rs.getString("usersjoined");
            String[] usersJoinedStringArray = usersJoinedString.split(",");
            usersJoined = new ArrayList<>();

            for (String user : usersJoinedStringArray){
                usersJoined.add(user);
            }   
        }
        Hike hike = new Hike(id, host, country, hikeSpotName, dateAndTime, sunriseTime, sunsetTime, usersJoined);

        return hike;  
    }

    // insert hike
    /* public Boolean insertHike(Hike hike){
        Integer rowsUpdated = template.update(SQL_Queries.insertHike_SQL,
            hike.getHost(), hike.getCountry(), hike.getHikeSpotName(),
            String.valueOf(hike.getDateAndTime().getTime()), String.valueOf(hike.getSunriseTime().getTime()), String.valueOf(hike.getSunsetTime().getTime()));
        
        if (rowsUpdated > 0){
            return true;
        }
        return false;
    } */


    public Hike insertHike(Hike hike){
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        Integer rowsUpdated = template.update( connection ->  {
                                        PreparedStatement ps = connection.prepareStatement(SQL_Queries.insertHike_SQL,Statement.RETURN_GENERATED_KEYS);
                                        ps.setString(1, hike.getHost());
                                        ps.setString(2,hike.getCountry());
                                        ps.setString(3,hike.getHikeSpotName());
                                        ps.setString(4,String.valueOf(hike.getDateAndTime().getTime()));
                                        ps.setString(5,String.valueOf(hike.getSunriseTime().getTime()));
                                        ps.setString(6,String.valueOf(hike.getSunsetTime().getTime()));

                                        return ps;
                                        }, keyHolder);

        Integer id = keyHolder.getKey().intValue();

        Integer rowsUpdated2 = template.update(SQL_Queries.joinHike_SQL, id, hike.getHost());
        
        return getHike(id);
    }


    //getting hikelist
    public List<Hike> getHikeList(){
        List<Hike> hikeList = new ArrayList<>();

        SqlRowSet rs = template.queryForRowSet(SQL_Queries.getHikeList_SQL);
        while (rs.next()){
            Integer id = rs.getInt("hostedhike_id");
            String host = rs.getString("hostname");
            String country = rs.getString("country");
            String hikeSpotName = rs.getString("hikespotname");
            Date dateAndTime = new Date(Long.parseLong(rs.getString("dateandtime")));
            Date sunriseTime = new Date (Long.parseLong(rs.getString("sunrisetime")));
            Date sunsetTime = new Date(Long.parseLong(rs.getString("sunsettime")));
            
            List<String> usersJoined = new ArrayList<>();
            if (!(rs.getString("usersjoined") == null)){
                String usersJoinedString = rs.getString("usersjoined");
                String[] usersJoinedStringArray = usersJoinedString.split(",");
                usersJoined = new ArrayList<>();

                for (String user : usersJoinedStringArray){
                    usersJoined.add(user);
                }   
            }
            
            
            Hike hike = new Hike(id, host, country, hikeSpotName, dateAndTime, sunriseTime, sunsetTime, usersJoined);
            hikeList.add(hike);
        }
        return hikeList;
    }



    public List<Hike> getHikesJoined(String teleUsername){
        SqlRowSet rsA = template.queryForRowSet(SQL_Queries.getUsernameFromTeleUsername_SQL, teleUsername);
        rsA.next();
        String username = rsA.getString("username");

        List<Hike> hikeList = new ArrayList<>();

        SqlRowSet rs = template.queryForRowSet(SQL_Queries.getHikesJoined_SQL, username);
        while (rs.next()){
            Integer id = rs.getInt("hostedhike_id");

            Hike hike = getHike(id);
            hikeList.add(hike);
        }

        List<Hike> sortedHikeList = hikeList.stream().sorted(Comparator.comparing(Hike::getDateAndTime)).collect(Collectors.toList());
        return sortedHikeList;
    }

    public String convertHikeListToString(List<Hike> hikeList){
        System.out.println("length: " + hikeList.size());
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy h:mm a");
        StringBuilder sb = new StringBuilder();
        sb.append("Hike Details:\n");
        sb.append("---------------\n");
        
        for (Hike hike : hikeList){
            
            sb.append("Date & Time: ").append(dateFormat.format(hike.getDateAndTime())).append("\n");
            sb.append("Hike Spot: ").append(hike.getHikeSpotName()).append("\n");
            sb.append("Country: ").append(hike.getCountry()).append("\n");
            sb.append("Hosted by: ").append(hike.getHost()).append("\n");
            sb.append("Sunrise: ").append(dateFormat.format(hike.getSunriseTime())).append("\n");
            sb.append("Sunset: ").append(dateFormat.format(hike.getSunsetTime())).append("\n");
            sb.append("---------------\n");
            
        }
        return sb.toString();
    }
}
