package br.com.discordBot.tributes.service;

import br.com.discordBot.tributes.config.ConectaApiAlbion;
import br.com.discordBot.tributes.entity.Donations;
import br.com.discordBot.tributes.entity.Gathering;
import br.com.discordBot.tributes.entity.LifetimeStatistics;
import br.com.discordBot.tributes.entity.MemberTribute;
import br.com.discordBot.tributes.repository.MemberTributeRepository;
import br.com.discordBot.tributes.util.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Configurable
public class MemberTributeService {

    MemberTributeRepository memberTributeRepository;

    @Autowired
    GatheringService gatheringService;

    @Autowired
    LifetimeStatisticsService lifetimeStatisticsService;

    @Autowired
    public MemberTributeService(MemberTributeRepository memberTributeRepository) {
        this.memberTributeRepository = memberTributeRepository;
    }

    public JSONArray processJsonFromApi() {
        JSONArray jsonArray = new JSONArray(ConectaApiAlbion.loadTributes());
        return jsonArray;
    }

    public List<MemberTribute> buildListOfTributes(boolean update) {

        List<MemberTribute> listOfMemberTribute = new ArrayList<>();

        JSONArray jsonArrayInMemory = processJsonFromApi();

        for (int i = 0; i < jsonArrayInMemory.length(); i++) {

            /** Criando um novo Gathering, e salvando no banco **/
            Gathering gathering = gatheringService.buildingGatheringTributes(i, jsonArrayInMemory, update);

            /** Criando novo LifeTimeStatistics, e salvando no banco **/
            LifetimeStatistics lifetimeStatistics = lifetimeStatisticsService.buildingLifetimeStatistics(gathering, update);

            /** Criando MemberTribute  **/
            MemberTribute member = getMemberTribute(jsonArrayInMemory, i, lifetimeStatistics);

            List<MemberTribute> actualTributesList;
            List<MemberTribute> lastWeekTributesList;

            actualTributesList = memberTributeRepository.findMemberTributeById(member.getId(),
                    DataUtils.convertLocalDateToString(LocalDate.now()));

            lastWeekTributesList = memberTributeRepository.findMemberTributeById(member.getId(),
                    DataUtils.convertLocalDateToString(LocalDate.now().minusDays(1)));

            if (lastWeekTributesList.size() > 0) {
                for (MemberTribute atual : actualTributesList) {
                    for (MemberTribute lastWeek : lastWeekTributesList) {
                        Donations donations = new Donations();

                        if (lastWeek.getId().equals(atual.getId())) {

                            if (lastWeek.getLifetimeStatistics().getGathering().getTotalFibe() <
                                    atual.getLifetimeStatistics().getGathering().getTotalFibe()) {
                            }

                        } else {
                            System.out.println("LASTWEEK NAME: " + lastWeek.getName() + " | ATUAL NAME: " + atual.getName());
                        }
                    }
                }

            } else if (lastWeekTributesList.size() == 0){
                throw new NoResultException("There isn't record to process at last week");
            } else if (actualTributesList.size() == 0) {
                throw new NoResultException("There isn't record to process today");
            }

            listOfMemberTribute.add(member);

            if (update) {
                memberTributeRepository.save(member);
                log.info("Saving member " + member.getName().toUpperCase() + " - at: " +
                        member.getLifetimeStatistics().getTimestamp());
            }

        }

        return listOfMemberTribute;
    }

    private MemberTribute getMemberTribute(JSONArray jsonArrayInMemory, int i, LifetimeStatistics lifetimeStatistics) {
        MemberTribute member = new MemberTribute();
        member.setId(jsonArrayInMemory.getJSONObject(i).getString("Id"));
        member.setGuildName(jsonArrayInMemory.getJSONObject(i).getString("GuildName"));
        member.setName(jsonArrayInMemory.getJSONObject(i).getString("Name"));
        member.setLifetimeStatistics(lifetimeStatistics);
        return member;
    }

}
