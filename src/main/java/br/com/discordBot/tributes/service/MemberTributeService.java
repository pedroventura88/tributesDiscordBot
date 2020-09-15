package br.com.discordBot.tributes.service;

import br.com.discordBot.tributes.config.ConectaApiAlbion;
import br.com.discordBot.tributes.entity.Gathering;
import br.com.discordBot.tributes.entity.LifetimeStatistics;
import br.com.discordBot.tributes.entity.MemberTribute;
import br.com.discordBot.tributes.repository.MemberTributeRepository;
import org.json.JSONArray;
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

    @Autowired
    GatheringService gatheringService;

    @Autowired
    LifetimeStatisticsService lifetimeStatisticsService;

    public JSONArray processJsonFromApi() {
        JSONArray jsonArray = new JSONArray(ConectaApiAlbion.loadTributes());
        return jsonArray;
    }

    public List<MemberTribute> buildListOfTributes() {

        List<MemberTribute> listOfMemberTribute = new ArrayList<>();

        for (int i = 0; i < processJsonFromApi().length(); i++) {

            /** Criando um novo Gathering, e salvando no banco **/
            Gathering gathering = gatheringService.buildingGatheringTributes(i);

            /** Criando novo LifeTimeStatistics, e salvando no banco **/
            LifetimeStatistics lifetimeStatistics = lifetimeStatisticsService.buildingLifetimeStatistics(gathering);

            /** Criando MemberTribute  **/
            MemberTribute member = new MemberTribute();
            member.setId(processJsonFromApi().getJSONObject(i).getString("Id"));
            member.setGuildName(processJsonFromApi().getJSONObject(i).getString("GuildName"));
            member.setName(processJsonFromApi().getJSONObject(i).getString("Name"));
            member.setLifetimeStatistics(lifetimeStatistics);
            listOfMemberTribute.add(member);
            memberTributeRepository.save(member);
            System.out.println("Salvando o membro: " + member.getName());

        }

        return listOfMemberTribute;
    }

}
