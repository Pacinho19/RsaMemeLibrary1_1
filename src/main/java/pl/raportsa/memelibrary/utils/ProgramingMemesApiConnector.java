package pl.raportsa.memelibrary.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.raportsa.memelibrary.entity.ProgramingMeme;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProgramingMemesApiConnector {

    @Value("${app.programingMemesApiKey}")
    private String programingMemesApiKey;

    public List<ProgramingMeme> getRandomMemes() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://programming-memes-images.p.rapidapi.com/v1/memes"))
                    .header("x-rapidapi-host", "programming-memes-images.p.rapidapi.com")
                    .header("x-rapidapi-key", programingMemesApiKey)
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            String responseText = response.body();

            if (!responseText.isEmpty()) {
                Gson gson = new GsonBuilder().create();
                Type ibanType = new TypeToken<ArrayList<ProgramingMeme>>() {
                }.getType();
                return gson.fromJson(responseText, ibanType);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


}
