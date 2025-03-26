package FinalHikeFinder.FinalHikeFinder.service;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import FinalHikeFinder.FinalHikeFinder.repo.TeleLogicRepo;

@Service
public class TeleLogicService {

    
    @Autowired private TeleLogicRepo teleRepo;
    @Autowired private HikeService hikeService;


    //getHikesJoined


    //Bot State

    public Integer getBotState(String chatId){
        if (teleRepo.checkExists(chatId)){
            return Integer.parseInt(teleRepo.getValue(chatId));
        }

        initialiseBotState(chatId);
        return Integer.parseInt(teleRepo.getValue(chatId)); 
    }

    private void initialiseBotState(String chat_id){ //(helper)
        teleRepo.addValue(chat_id, "0");
    }

    public void setBotState(String chat_id, Integer state){
        teleRepo.addValue(chat_id, state.toString());
    }

    

    //Unique Identifiers
    
    public String getUniqueIdentifier(String username){
        String key = username + " UNIQUE_IDENTIFIER";
        String uniqueIdentifier = UUID.randomUUID().toString().substring(0,8);
        teleRepo.addValue(key, uniqueIdentifier);
        teleRepo.expireKey(key, Long.valueOf(30));
        return uniqueIdentifier;
    }

    public Boolean checkUniqueIdentifierMatch(String identifierInput, String username){
        String key = username + " UNIQUE_IDENTIFIER";
        if (teleRepo.checkExists(key)){
            String uniqueIdentifier = teleRepo.getValue(key);
            if (uniqueIdentifier.equals(identifierInput)){
                return true;
            }
            return false;
        }
        return false;
    }


    //Temporary Matching of teleUsername and Username

    public void insertTempUsernameMatch(String username, String teleUsername){
        String key = teleUsername + " USERNAME_MATCH";
        teleRepo.addValue(key, username);
    }

    public String getTempUsernameMatch(String teleUsername){
        String key = teleUsername + " USERNAME_MATCH";
        String username = teleRepo.getValue(key);
        return username;
    }

    public Boolean deleteTempUsernameMatch(String teleUsername){
        String key = teleUsername + " USERNAME_MATCH";
        Boolean isDeleted = teleRepo.deleteValue(key);
        return isDeleted;
    }
}
