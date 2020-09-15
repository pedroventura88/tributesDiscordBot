package br.com.discordBot.tributes.service;

import br.com.discordBot.tributes.config.ConectaApiAlbion;
import br.com.discordBot.tributes.entity.Gathering;
import br.com.discordBot.tributes.entity.LifetimeStatistics;
import br.com.discordBot.tributes.entity.MemberTribute;
import br.com.discordBot.tributes.repository.MemberTributeRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Configurable
public class MemberTributeService {

    MemberTributeRepository memberTributeRepository;

    @Autowired
    public MemberTributeService(MemberTributeRepository memberTributeRepository) {
        this.memberTributeRepository = memberTributeRepository;
    }

    public String loadTributesFromApi() {
        return ConectaApiAlbion.loadTributes();
    }

    public JSONArray processJsonFromApi() {
        JSONArray jsonArray = new JSONArray(loadTributesFromApi());
        return jsonArray;
    }

    public List<MemberTribute> buildListOfTributes() {

        List<MemberTribute> listOfMemberTribute = new ArrayList<>();

        for (int i = 0; i < processJsonFromApi().length(); i++) {

            MemberTribute member = new MemberTribute();
            member.setId(processJsonFromApi().getJSONObject(i).getString("Id"));
            member.setGuildName(processJsonFromApi().getJSONObject(i).getString("GuildName"));
            member.setName(processJsonFromApi().getJSONObject(i).getString("Name"));
            Gathering gathering = buidingGathering(i);
            member.setLifetimeStatistics(new LifetimeStatistics(gathering, LocalDateTime.now()));
            listOfMemberTribute.add(member);
            memberTributeRepository.save(member);
            System.out.println("Salvando o membro: " + member.getName());

        }

        return listOfMemberTribute;
    }

    public Gathering buidingGathering(int i) {
        Gathering gathering = new Gathering();
        JSONObject PATH_TO_GATHERING = processJsonFromApi()
                .getJSONObject(i)
                .getJSONObject("LifetimeStatistics")
                .getJSONObject("Gathering");

        gathering.setTotalFibe(PATH_TO_GATHERING.getJSONObject("Fiber").getLong("Total"));
        gathering.setTotalHide(PATH_TO_GATHERING.getJSONObject("Hide").getLong("Total"));
        gathering.setTotalOre(PATH_TO_GATHERING.getJSONObject("Ore").getLong("Total"));
        gathering.setTotalRock(PATH_TO_GATHERING.getJSONObject("Rock").getLong("Total"));
        gathering.setTotalWood(PATH_TO_GATHERING.getJSONObject("Wood").getLong("Total"));
        return gathering;
    }
}
