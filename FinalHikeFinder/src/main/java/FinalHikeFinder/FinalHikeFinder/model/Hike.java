package FinalHikeFinder.FinalHikeFinder.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;  

public class Hike {
        
    private Integer id;
    private String host;
    private String country;
    private String hikeSpotName;
    private Date dateAndTime;
    private Date sunriseTime;
    private Date sunsetTime;
    private List<String> usersJoined;
    

    public Hike() {
        this.usersJoined = new ArrayList<String>();
    }


    
    public Hike(String host, String country, String hikeSpotName, Date dateAndTime, Date sunriseTime,
        Date sunsetTime) {
    this.host = host;
    this.country = country;
    this.hikeSpotName = hikeSpotName;
    this.dateAndTime = dateAndTime;
    this.sunriseTime = sunriseTime;
    this.sunsetTime = sunsetTime;
    List<String> emptyUsersJoined = new ArrayList<>();
    emptyUsersJoined.add(host);
    this.usersJoined = emptyUsersJoined;
    }


    public Hike(Integer id, String host, String country, String hikeSpotName, Date dateAndTime, Date sunriseTime,
            Date sunsetTime, List<String> usersJoined) {
        this.id = id;
        this.host = host;
        this.country = country;
        this.hikeSpotName = hikeSpotName;
        this.dateAndTime = dateAndTime;
        this.sunriseTime = sunriseTime;
        this.sunsetTime = sunsetTime;
        this.usersJoined = usersJoined;
    }

    public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getHost() {
		return host;
	}


	public void setHost(String host) {
		this.host = host;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getHikeSpotName() {
		return hikeSpotName;
	}


	public void setHikeSpotName(String hikeSpotName) {
		this.hikeSpotName = hikeSpotName;
	}


	public Date getDateAndTime() {
		return dateAndTime;
	}


	public void setDateAndTime(Date dateAndTime) {
		this.dateAndTime = dateAndTime;
	}


	public Date getSunriseTime() {
		return sunriseTime;
	}


	public void setSunriseTime(Date sunriseTime) {
		this.sunriseTime = sunriseTime;
	}


	public Date getSunsetTime() {
		return sunsetTime;
	}


	public void setSunsetTime(Date sunsetTime) {
		this.sunsetTime = sunsetTime;
	}


	public List<String> getUsersJoined() {
		return usersJoined;
	}


	public void setUsersJoined(List<String> usersJoined) {
		this.usersJoined = usersJoined;
	}


	// Extra functions
    public Boolean contains(String userNameString){
            return usersJoined.contains(userNameString);
    }
    public Boolean isHost(String userNameString){
        return host.equals(userNameString);
    }

}

