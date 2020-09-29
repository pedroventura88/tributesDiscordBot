package br.com.discordBot.tributes.service;

import br.com.discordBot.tributes.config.ConectaApiAlbion;
import br.com.discordBot.tributes.entity.Donations;
import br.com.discordBot.tributes.entity.Gathering;
import br.com.discordBot.tributes.entity.LifetimeStatistics;
import br.com.discordBot.tributes.entity.MemberTribute;
import br.com.discordBot.tributes.repository.MemberTributeRepository;
import br.com.discordBot.tributes.util.DataUtils;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@Configurable
public class MemberTributeService {

    MemberTributeRepository memberTributeRepository;

    @Autowired
    DonationsService donationsService;

    @Autowired
    GatheringService gatheringService;

    @Autowired
    LifetimeStatisticsService lifetimeStatisticsService;

    @Autowired
    public MemberTributeService(MemberTributeRepository memberTributeRepository) {
        this.memberTributeRepository = memberTributeRepository;
    }

    public List<MemberTribute> buildListOfTributes(boolean update, JSONArray jsonArrayInMemory) throws UnirestException {

        List<MemberTribute> listOfMemberTribute = new ArrayList<>();

        for (int i = 0; i < jsonArrayInMemory.length(); i++) {

            /** Criando um novo Gathering, e salvando no banco **/
            Gathering gathering = gatheringService.buildingGatheringTributes(i, jsonArrayInMemory, update);

            /** Criando novo LifeTimeStatistics, e salvando no banco **/
            LifetimeStatistics lifetimeStatistics = lifetimeStatisticsService.buildingLifetimeStatistics(gathering, update);

            /** Criando MemberTribute  **/
            MemberTribute member = getMemberTribute(jsonArrayInMemory, i, lifetimeStatistics, update);

//            List<MemberTribute> actualTributesList;
//            List<MemberTribute> lastWeekTributesList;
//
//            actualTributesList = memberTributeRepository.findMemberTributeById(member.getId(),
//                    DataUtils.convertLocalDateToString(LocalDate.now().minusDays(1)));
//
//            lastWeekTributesList = memberTributeRepository.findMemberTributeById(member.getId(),
//                    DataUtils.convertLocalDateToString(LocalDate.now().minusDays(2)));

            /** OPS.. Qual lista passar aqui?? acho que deve ser doações..**/
            listOfMemberTribute.add(member);

            if (update) {
                memberTributeRepository.save(member);
                log.info("Saving member " + member.getName().toUpperCase() + " - at: " +
                        member.getLifetimeStatistics().getTimestamp());
            }

        }

        return listOfMemberTribute;
    }

    public MemberTribute getMemberTribute(JSONArray jsonArrayInMemory, int i, LifetimeStatistics lifetimeStatistics, boolean update) {
        MemberTribute member = new MemberTribute();
        member.setId(jsonArrayInMemory.getJSONObject(i).getString("Id"));
        member.setGuildName(jsonArrayInMemory.getJSONObject(i).getString("GuildName"));
        member.setName(jsonArrayInMemory.getJSONObject(i).getString("Name"));
        member.setLifetimeStatistics(lifetimeStatistics);

        if (update) {
            memberTributeRepository.save(member);
        }

        return member;
    }

}
