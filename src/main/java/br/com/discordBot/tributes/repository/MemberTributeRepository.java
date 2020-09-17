package br.com.discordBot.tributes.repository;

import br.com.discordBot.tributes.entity.MemberTribute;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberTributeRepository extends CrudRepository<MemberTribute, Long> {

    @Query(value = "SELECT * " +
            " FROM TB_MEMBER_TRIBUTE TMT, LIFETIME_STATISTICS LS, GATHERING G2 " +
            " WHERE TMT.ID_LIFETIMESTATISTICS = LS.ID_LIFETIMESTATISTICS " +
            " AND LS.ID_GATHERING = G2.ID_GATHERING " +
            " AND TMT.MEMBER_ID = :memberId " +
            " AND CAST(LS.TIMESTAMP_LIFETIMESTATISTICS AS DATE) = :timestamp ", nativeQuery = true)
    List<MemberTribute> findMemberTributeById(@Param("memberId") String memberId, @Param("timestamp") String timestamp);

}
