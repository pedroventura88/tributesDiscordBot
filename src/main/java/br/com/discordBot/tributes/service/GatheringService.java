package br.com.discordBot.tributes.service;

import br.com.discordBot.tributes.config.ConectaApiAlbion;
import br.com.discordBot.tributes.entity.Gathering;
import br.com.discordBot.tributes.repository.GatheringRepository;
import lombok.NoArgsConstructor;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

@Service
@Configurable
@NoArgsConstructor
public class GatheringService {

    GatheringRepository gatheringRepository;

    @Autowired
    public GatheringService(GatheringRepository gatheringRepository) {
        this.gatheringRepository = gatheringRepository;
    }

    public Gathering buildingGatheringTributes(int i, JSONArray jsonArray, boolean update) {
        Gathering gathering = new Gathering();
        JSONObject PATH_TO_GATHERING = jsonArray
                .getJSONObject(i)
                .getJSONObject("LifetimeStatistics")
                .getJSONObject("Gathering");

        long leftLimit = 400L;
        long rightLimit = 1200L;

//        gathering.setTotalFibe(PATH_TO_GATHERING.getJSONObject("Fiber").getLong("Total"));
//        gathering.setTotalHide(PATH_TO_GATHERING.getJSONObject("Hide").getLong("Total"));
//        gathering.setTotalOre(PATH_TO_GATHERING.getJSONObject("Ore").getLong("Total"));
//        gathering.setTotalRock(PATH_TO_GATHERING.getJSONObject("Rock").getLong("Total"));
//        gathering.setTotalWood(PATH_TO_GATHERING.getJSONObject("Wood").getLong("Total"));

        gathering.setTotalFibe(new RandomDataGenerator().nextLong(leftLimit, rightLimit));
        gathering.setTotalHide(new RandomDataGenerator().nextLong(leftLimit, rightLimit));
        gathering.setTotalOre(new RandomDataGenerator().nextLong(leftLimit, rightLimit));
        gathering.setTotalRock(new RandomDataGenerator().nextLong(leftLimit, rightLimit));
        gathering.setTotalWood(new RandomDataGenerator().nextLong(leftLimit, rightLimit));

        if (update) {
            gatheringRepository.save(gathering);
        }

        return gathering;
    }
}
