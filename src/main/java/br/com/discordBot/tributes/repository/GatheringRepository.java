package br.com.discordBot.tributes.repository;

import br.com.discordBot.tributes.entity.Gathering;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GatheringRepository extends CrudRepository<Gathering, Long> {
}
