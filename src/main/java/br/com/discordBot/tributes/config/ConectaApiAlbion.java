package br.com.discordBot.tributes.config;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Slf4j
@Configuration
public class ConectaApiAlbion {

    private static final String URL = "https://gameinfo.albiononline.com/api/gameinfo/guilds/";

    public static String getGuildId(String guildId) {
        return URL + guildId + "/members";
    }

    public static String loadTributes() throws UnirestException {

        Unirest.setTimeouts(240000, 240000);
        HttpResponse<String> response = Unirest.get(getGuildId("eElTYLvuRpSGDipdZxjewA")).asString();

        if (response.getStatus() != 200) {
            throw new UnirestException("Falha ao carregar os tributos da API");
        }
        return response.getBody().toString();

    }

}
