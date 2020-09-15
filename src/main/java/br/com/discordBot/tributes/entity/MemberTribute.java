package br.com.discordBot.tributes.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "TB_MEMBER_TRIBUTE")
public class MemberTribute implements Serializable {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TB_MEMBER_ID")
    private Long idMember;

    @Column(name = "MEMBER_NAME")
    private String name;

    @Column(name = "MEMBER_ID")
    private String id;

    @Column(name = "GUILD_NAME")
    private String guildName;

    @Column(name = "GUILD_ID")
    private String guildId;

    @Column(name = "ALLIANCE_NAME")
    private String allianceName;

    @Column(name = "ALLIANCE_ID")
    private String allianceId;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_LIFETIMESTATISTICS")
    private LifetimeStatistics lifetimeStatistics;
}
