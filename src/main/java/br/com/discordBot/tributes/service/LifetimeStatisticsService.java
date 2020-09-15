package br.com.discordBot.tributes.service;

import br.com.discordBot.tributes.entity.Gathering;
import br.com.discordBot.tributes.entity.LifetimeStatistics;
import br.com.discordBot.tributes.repository.LifetimeStatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Configurable
public class LifetimeStatisticsService {

    LifetimeStatisticsRepository repository;

    @Autowired
    public LifetimeStatisticsService(LifetimeStatisticsRepository repository) {
        this.repository = repository;
    }

    public LifetimeStatistics buildingLifetimeStatistics (Gathering gathering) {
        LifetimeStatistics lifetimeStatistics = new LifetimeStatistics();
        lifetimeStatistics.setGathering(gathering);
        lifetimeStatistics.setTimestamp(LocalDateTime.now());
        repository.save(lifetimeStatistics);
        return lifetimeStatistics;
    }
}
