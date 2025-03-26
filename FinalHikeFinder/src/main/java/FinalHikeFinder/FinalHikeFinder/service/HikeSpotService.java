package FinalHikeFinder.FinalHikeFinder.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import FinalHikeFinder.FinalHikeFinder.model.HikeSpot;
import FinalHikeFinder.FinalHikeFinder.repo.HikeSpotsRepo;
import FinalHikeFinder.FinalHikeFinder.repo.constants.MongoConstants;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class HikeSpotService {
    
    @Autowired HikeSpotsRepo hikeSpotsRepo;

    /* @Value("${google.api.key}") String googleApiKey; */


    /* //finding center coordinate of map
    public String getCenterCoordinates(List<HikeSpot> hikeSpotList){
        Double latSum = 0.0;
        Double lngSum = 0.0;
        for (HikeSpot hikeSpot : hikeSpotList){
            latSum += hikeSpot.getLat();
            lngSum += hikeSpot.getLng();
        }
        Double latAve = latSum/hikeSpotList.size();
        Double lngAve = lngSum/hikeSpotList.size();
        
        String output = String.valueOf(latAve) + "," + String.valueOf(lngAve);
        return output;
    } */


    /* //getting map zoom level
    public String getMapZoom(String filterBy){
        if (filterBy.equals("Japan")){
            return "4";
        }
        else if (filterBy.equals("India")){
            return "3.5";
        }
        else if (filterBy.equals("Singapore")) {
            return "10";
        }
        return "3";
    } */


    /* //getting google map api link
    public String getGoogleMapApiUrl(){
        String googleMapApiUrl = UriComponentsBuilder
                        .fromUriString("https://maps.googleapis.com/maps/api/js")
                        .queryParam("key", googleApiKey)
                        .queryParam("callback","initMap")
                        .toUriString();
        return googleMapApiUrl;
    } */


    /* //getting google map marker link
    public String getGoogleMapMarkerUrl(){
        String googleMapMarkerUrl = UriComponentsBuilder
                        .fromUriString("https://maps.googleapis.com/maps/api/js")
                        .queryParam("key", googleApiKey)
                        .queryParam("libraries","maps,marker")
                        .queryParam("v","beta")
                        .toUriString();
        return googleMapMarkerUrl;
    } */


    //filtering hikespotlist by country
    public List<HikeSpot> getFilteredHikeSpotList(String filterBy){
        List<HikeSpot> hikeSpotList = getHikeSpotList();
        List<HikeSpot> filteredHikeSpotList = new ArrayList<>();
        if (filterBy.equals("Japan") || filterBy.equals("Singapore") || filterBy.equals("India")){
            filteredHikeSpotList = hikeSpotList.stream().filter(hikeSpot -> hikeSpot.getCountry().equals(filterBy)).toList();
        }
        else{
            filteredHikeSpotList = hikeSpotList.stream().toList();
        }
        return filteredHikeSpotList;        
    }


    //get one hikespot
    public HikeSpot getHikeSpot(Integer hikeSpotId){
        Document document = hikeSpotsRepo.getHikeSpot(hikeSpotId); 
        HikeSpot hikeSpot = convertDocumentToHikeSpot(document);
        return hikeSpot;
    }

    
    //get hikespotlist
    public List<HikeSpot> getHikeSpotList(){
        List<Document> hikeSpotsDocumentList = hikeSpotsRepo.getHikeSpotDocumentList();
        List<HikeSpot> hikeSpotList = new ArrayList<>();
        for (Document hikeSpotDocument : hikeSpotsDocumentList){
            HikeSpot hikeSpot = convertDocumentToHikeSpot(hikeSpotDocument);
            hikeSpotList.add(hikeSpot);
        }
        return hikeSpotList;
    }


    //converting document to hikespot
    private HikeSpot convertDocumentToHikeSpot(Document hikeSpotDocument){
        
        Integer id = hikeSpotDocument.getInteger("_id");
        Double lat = Double.parseDouble(hikeSpotDocument.getString("lat"));
        Double lng = Double.parseDouble(hikeSpotDocument.getString("lng"));
        String name = hikeSpotDocument.getString("name");
        String description = hikeSpotDocument.getString("description");
        String country = hikeSpotDocument.getString("country");
        String timeZone = hikeSpotDocument.getString("timeZone");

        List<String> visitorList = new ArrayList<>();
        String [] visitorListString = hikeSpotDocument.getString("visitList").replace("[","").replace("]","").split(",");
        for (String visitorString : visitorListString ){
            visitorList.add(visitorString.trim());
        }

        HikeSpot hikeSpot = new HikeSpot(id, lat, lng, name, description,country,timeZone,visitorList);
        return hikeSpot;
    }


    //insert all initial hikespots into mongo
    public void initialiseHikeSpots() throws IOException{
        Long count = hikeSpotsRepo.checkCount();
        if (count <= 0){
            List<Document> hikeSpotsDocumentList = getHikeSpotsDocumentList(MongoConstants.hikeSpotJsonDatapath);
            hikeSpotsRepo.batchInsert(hikeSpotsDocumentList);
        }       
    }


    //converting json array to document list
    private List<Document> getHikeSpotsDocumentList(String inputFilePath) throws IOException{
        JsonArray hikeSpotJsonArray = getHikeSpotJsonArray(inputFilePath);

        List<Document> documentList = new ArrayList<>();
        for (int i = 0; i < hikeSpotJsonArray.size(); i++ ){
            JsonObject hikeSpotJsonObject = hikeSpotJsonArray.getJsonObject(i);
            Document hikeSpotDocument = Document.parse(hikeSpotJsonObject.toString());
            documentList.add(hikeSpotDocument);
        }
        return documentList;
    }


    //converting the read string into json array
    private JsonArray getHikeSpotJsonArray(String inputFilePath) throws IOException{
        String hikeSpotJsonString = readHikeSpotJson(new File(inputFilePath));
        JsonReader reader = Json.createReader(new StringReader(hikeSpotJsonString));
        JsonArray hikeSpotJsonArray = reader.readArray();
        return hikeSpotJsonArray;
    }


    //reading hikespotjson file
    private String readHikeSpotJson(File inputFilePath) throws IOException{
		FileReader fr = new FileReader(inputFilePath);
		BufferedReader br = new BufferedReader(fr);
		
		String fullJsonString = "";
		String line = "";
		while ((line = br.readLine()) != null){
			fullJsonString += line;
		}
		br.close();
		fr.close();

		return fullJsonString;
	}

    //get one hikespot for tele
    public HikeSpot getHikeSpotTele(String hikeSpotName){
        Document document = hikeSpotsRepo.getHikeSpotTele(hikeSpotName); 
        HikeSpot hikeSpot = convertDocumentToHikeSpot(document);
        return hikeSpot;
    }
}
