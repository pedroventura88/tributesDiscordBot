package br.com.discordBot.tributes.config;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConectaApiAlbion {

    private static final String URL = "https://gameinfo.albiononline.com/api/gameinfo/guilds/";

    public static String buildNameGuild(String nameGuild) {
        return URL + nameGuild + "/members";
    }

    public static String loadTributes() {

        try {
            HttpResponse<String> response = Unirest.get(buildNameGuild("eElTYLvuRpSGDipdZxjewA")).asString();
            return response.getBody().toString();

        } catch (UnirestException e) {
            return "Falha ao carregar os Tributos da API: " + e;
        }

    }
}
