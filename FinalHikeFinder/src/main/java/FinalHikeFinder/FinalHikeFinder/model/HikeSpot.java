package FinalHikeFinder.FinalHikeFinder.model;

import java.util.ArrayList;
import java.util.List;

public class HikeSpot {
    private Integer id;
    private Double lat;
    private Double lng;
    private String name;
    private String description;
    private String country;
    private String timeZone;
    private List<String> visitList;

    
    public HikeSpot() {
        this.visitList = new ArrayList<String>();
    } 

    public HikeSpot(Integer id, Double lat, Double lng, String name, String description, String country,
            String timeZone, List<String> visitList) {
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.name = name;
        this.description = description;
        this.country = country;
        this.timeZone = timeZone;
        this.visitList = visitList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }



    public void setLat(Double lat) {
        this.lat = lat;
    }



    public Double getLng() {
        return lng;
    }



    public void setLng(Double lng) {
        this.lng = lng;
    }



    public String getName() {
        return name;
    }



    public void setName(String name) {
        this.name = name;
    }



    public String getDescription() {
        return description;
    }



    public void setDescription(String description) {
        this.description = description;
    }



    public String getCountry() {
        return country;
    }



    public void setCountry(String country) {
        this.country = country;
    }


    public String getTimeZone() {
        return timeZone;
    }



    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }



    public List<String> getVisitList() {
        return visitList;
    }



    public void setVisitList(List<String> visitList) {
        this.visitList = visitList;
    }

    
    //Need this for thymeleaf to work
    public String getLatLngString(){
        String latLngString = String.valueOf(this.lat) + ", " + String.valueOf(this.lng);
        return latLngString;
    }

    @Override
    public String toString(){
        String result = "[" + id + "," + lat + "," + lng + "," + name + "," + description + "," + country + "," + timeZone + "," + visitList.toString() + "]";
        return result;
    }

    
}