package FinalHikeFinder.FinalHikeFinder.repo;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import FinalHikeFinder.FinalHikeFinder.repo.constants.RedisConstants;

@Repository
public class TeleLogicRepo {
    
    @Autowired
    @Qualifier(RedisConstants.template01)
    private RedisTemplate<String,String> template;

    public void addValue(String key, String value){
        template.opsForValue().set(key,value);
    }

    public String getValue(String key){
        return template.opsForValue().get(key);
    }

    public Boolean deleteValue(String key){
        return template.delete(key);
    }

    public Boolean expireKey(String key, Long seconds){
        return template.expire(key, seconds, TimeUnit.SECONDS);
    }

    public long incrementValue (String key, Integer value){
        return template.opsForValue().increment(key, value);
    }

    public long decrementValue (String key, Integer value){
        return template.opsForValue().decrement(key, value);
    }

    public Boolean checkExists(String key){
        return template.hasKey(key);
    }
}
