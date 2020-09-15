package br.com.discordBot.tributes.repository;

import br.com.discordBot.tributes.entity.LifetimeStatistics;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LifetimeStatisticsRepository extends CrudRepository<LifetimeStatistics, Long> {
}
