package br.com.discordBot.tributes.repository;

import br.com.discordBot.tributes.entity.Gathering;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GatheringRepository extends CrudRepository<Gathering, Long> {

    @Query(value = "SELECT * FROM GATHERING G WHERE G.ID_GATHERING >= :idGatheding", nativeQuery = true)
    List<Gathering> findGatheringByIdGathering(@Param("idGatheding") Long idGatheding);
}
