package br.com.discordBot.tributes.config;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ConectaApiAlbion {

    private static final String URL = "https://gameinfo.albiononline.com/api/gameinfo/guilds/";

    public static String getGuildId(String guildId) {
        return URL + guildId + "/members";
    }

    public static String loadTributes() {

        try {
            Unirest.setTimeouts(240000, 240000);
            HttpResponse<String> response = Unirest.get(getGuildId("eElTYLvuRpSGDipdZxjewA")).asString();
            return response.getBody().toString();

        } catch (UnirestException e) {
            log.error("Failed to load tributes from API: ", e);
            return null;
        }

    }
}
