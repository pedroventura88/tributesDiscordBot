package br.com.discordBot.tributes.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class Gathering implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_GATHERING")
    private Long idGathering;

    @Column(name = "TOTAL_FIBE")
    private Long totalFibe;

    @Column(name = "TOTAL_HIDE")
    private Long totalHide;

    @Column(name = "TOTAL_ORE")
    private Long totalOre;

    @Column(name = "TOTAL_ROCK")
    private Long totalRock;

    @Column(name = "TOTAL_WOOD")
    private Long totalWood;
}
