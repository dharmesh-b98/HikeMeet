package FinalHikeFinder.FinalHikeFinder.service;

import java.io.StringReader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

@Service
public class GeminiService {

    RestTemplate template = new RestTemplate();

    @Value("${gemini.api.key}") String geminiApiKey;
    
    public String getGeminiResponse(String userRequestMessage){

        String url = UriComponentsBuilder.fromUriString("https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent")
                                            .queryParam("key", geminiApiKey)
                                            .build().toString();

        RequestEntity<String> request = RequestEntity.post(url)
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .body(getJsonRequestBody(userRequestMessage));

        ResponseEntity<String> response = template.exchange(request, String.class);

        String geminiResponse = decodeGeminiResponse(response.getBody());
        return geminiResponse;                                           
    }


    private String decodeGeminiResponse(String responseJsonString){
        JsonObject responseJson = Json.createReader(new StringReader(responseJsonString)).readObject();
        JsonObject candidates = responseJson.getJsonArray("candidates").get(0).asJsonObject();
        JsonObject parts = candidates.getJsonObject("content").getJsonArray("parts").get(0).asJsonObject();
        String responseString = parts.getString("text");
        return responseString;
    }


    private String getJsonRequestBody(String userRequestMessage){
        /* String samplejson = 
        """             
                {   
            "contents": [{
                "parts":[{"text": "Explain how AI works"}]
            }]
        }
        """; */

        JsonObject text = Json.createObjectBuilder().add("text", userRequestMessage).build();
        JsonArray textArray = Json.createArrayBuilder().add(text).build();
        JsonObject parts = Json.createObjectBuilder().add("parts",textArray).build();
        JsonArray partsArray = Json.createArrayBuilder().add(parts).build();
        JsonObject contents = Json.createObjectBuilder().add("contents", partsArray).build();
        return contents.toString();
    }
}
