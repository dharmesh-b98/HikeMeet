package FinalHikeFinder.FinalHikeFinder.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import FinalHikeFinder.FinalHikeFinder.model.User;
import FinalHikeFinder.FinalHikeFinder.repo.constants.SQL_Queries;

@Repository
public class UserRepo {
    @Autowired JdbcTemplate template;


    //update telegram info
    public Boolean updateTelegramInfo(String telegramUsername, String chatId, String username){
        System.out.println(telegramUsername);
        System.out.println(chatId);
        System.out.println(username);
        Integer rowsUpdated = template.update(SQL_Queries.updateTelegramInfo_SQL, telegramUsername, chatId, username);

        if (rowsUpdated > 0){
            return true;
        }
        return false;
    }


    //check if username exists (helper)
    public Boolean checkUserExists(String username){
        SqlRowSet rs = template.queryForRowSet(SQL_Queries.checkUserExists_SQL, username);
        rs.next();
        Integer count = rs.getInt("count");
        return (count>0);
    }


    //getUser //returns a user with empty attributes if username does not exist
    public User getUser(String username){
        SqlRowSet rs = template.queryForRowSet(SQL_Queries.getUser_SQL, username);
        List<Integer> hostedHikes = new ArrayList<>();
        String username1=null;
        String password=null;
        String role=null;
        String telegramUsername=null;
        String chatId=null;
        while (rs.next()){
            username1 = rs.getString("username");
            password = rs.getString("password");
            role = rs.getString("role");
            telegramUsername = rs.getString("telegram_username");
            chatId = rs.getString("chat_id");
            Integer hostedhikeId = rs.getInt("hostedhike_id");
            hostedHikes.add(hostedhikeId);
        }
        return new User(username1, password, role, telegramUsername, chatId, hostedHikes);
    }


    //NEW USER
    public Boolean insertNewUser(User user){
        Integer rowsUpdated = template.update(SQL_Queries.insertNewUser_SQL, user.getUsername(), user.getPassword(), user.getRole());
        return (rowsUpdated>0);
    }


    //check if initial users initialised
    public Boolean isInitialised(){
        SqlRowSet rs = template.queryForRowSet(SQL_Queries.checkUserInitialised_SQL);
        rs.next();
        Integer count = rs.getInt("count");
        return (count>0);
    }

    
}
