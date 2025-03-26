package FinalHikeFinder.FinalHikeFinder.repo.constants;

public class SQL_Queries {
    
    //USER
    public static final String checkUserExists_SQL = "select count(username) as count from users where username=?";

    public static final String insertNewUser_SQL = "insert into users(username, password, role) values (?,?,?)";
            
    public static final String checkUserInitialised_SQL = "select count(*) as count from users";
    
    public static final String getUser_SQL = """
                                                select u.username, u.password, u.role, u.telegram_username, u.chat_id, h.hostedhike_id
                                                from users as u left join hostedhikes as h
                                                on u.username = h.hostname
                                                where u.username = ?;
                                            """;

    public static final String updateTelegramInfo_SQL = """
                                                            update users set
                                                            telegram_username = ?,
                                                            chat_id = ?
                                                            where username = ?;
                                                        """;
      
                                        
                                                        
    //HIKE  

    public static final String unjoinHike_SQL = "delete from usersjoined where hostedhike_id = ? and username = ?";

    public static final String joinHike_SQL = "insert into usersjoined(hostedhike_id, username) values (?,?)";

    public static final String deleteHike_SQL = "delete from hostedhikes where hostedhike_id = ?";

    public static final String getHike_SQL = """
                                                select  
                                                h.hostedhike_id,
                                                h.hostname,
                                                h.country, 
                                                h.hikespotname,
                                                h.dateandtime, 
                                                h.sunrisetime, 
                                                h.sunsettime,
                                                group_concat(distinct u.username order by u.username separator ',') as usersjoined
                                                from hostedhikes as h 
                                                left join usersjoined as u 
                                                on h.hostedhike_id = u.hostedhike_id
                                                group by h.hostedhike_id,h.hostname, h.country, h.hikespotname, h.dateandtime, h.sunrisetime, h.sunsettime
                                                having h.hostedhike_id = ?;
                                            """;

    public static final String insertHike_SQL = """
                                                    insert into hostedhikes(hostname, country, hikespotname, dateandtime, sunrisetime, sunsettime)
                                                    values(?,?,?,?,?,?)
                                                """;

    public static final String getHikeList_SQL = """
                                                    select  
                                                    h.hostedhike_id,
                                                    h.hostname,
                                                    h.country, 
                                                    h.hikespotname,
                                                    h.dateandtime, 
                                                    h.sunrisetime, 
                                                    h.sunsettime,
                                                    group_concat(distinct u.username order by u.username separator ',') as usersjoined
                                                    from hostedhikes as h 
                                                    left join usersjoined as u 
                                                    on h.hostedhike_id = u.hostedhike_id
                                                    group by h.hostedhike_id,h.hostname, h.country, h.hikespotname, h.dateandtime, h.sunrisetime, h.sunsettime;
                                                """;

    public static final String getHikesJoined_SQL = "select hostedhike_id from usersjoined where username=?";

    public static final String getUsernameFromTeleUsername_SQL = "select username from users where telegram_username = ?";
}   
