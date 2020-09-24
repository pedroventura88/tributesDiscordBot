package br.com.discordBot.tributes.service;

import br.com.discordBot.tributes.config.ConectaApiAlbion;
import br.com.discordBot.tributes.entity.Gathering;
import br.com.discordBot.tributes.entity.LifetimeStatistics;
import br.com.discordBot.tributes.entity.MemberTribute;
import br.com.discordBot.tributes.repository.GatheringRepository;
import br.com.discordBot.tributes.repository.MemberTributeRepository;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FirstRegisterService {

    MemberTributeRepository memberTributeRepository;

    GatheringRepository gatheringRepository;

    @Autowired
    GatheringService gatheringService;

    @Autowired
    LifetimeStatisticsService lifetimeStatisticsService;

    @Autowired
    MemberTributeService memberTributeService;

    @Autowired
    public FirstRegisterService(MemberTributeRepository memberTributeRepository, GatheringRepository gatheringRepository) {
        this.memberTributeRepository = memberTributeRepository;
        this.gatheringRepository = gatheringRepository;
    }

    public List<MemberTribute> buildFirstInsertMembers(JSONArray jsonArrayInMemory) throws Exception {

        if (!verifyIfDataExists()) {
            List<MemberTribute> listOfMemberTribute = new ArrayList<>();

            for (int i = 0; i < jsonArrayInMemory.length(); i++) {

                Gathering gathering = gatheringService.buildingGatheringTributes(i, jsonArrayInMemory, true);

                LifetimeStatistics lifetimeStatistics = lifetimeStatisticsService
                        .buildingLifetimeStatistics(gathering, true);

                MemberTribute memberTribute = memberTributeService
                        .getMemberTribute(jsonArrayInMemory, i, lifetimeStatistics, true);

                listOfMemberTribute.add(memberTribute);
                memberTributeRepository.save(memberTribute);
                log.info("FIRST INSERT. User: " + memberTribute.getName().toUpperCase());

            }

            return listOfMemberTribute;
        }

        throw new Exception("Esse comando não pode ser executado pois já existem registros no banco de dados.");

    }

    private Boolean verifyIfDataExists() {
        List<Gathering> list = gatheringRepository.findGatheringByIdGathering(1L);
        if (list.size() >= 1) {
            return true;
        } else {
            return false;
        }
    }
}
