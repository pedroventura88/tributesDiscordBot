package br.com.discordBot.tributes.repository;

import br.com.discordBot.tributes.entity.MemberTribute;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberTributeRepository extends CrudRepository<MemberTribute, Long> {

}
