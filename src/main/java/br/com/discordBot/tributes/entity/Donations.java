package br.com.discordBot.tributes.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Donations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String memberName;
    private Long lastWeekResource;
    private Long atualResource;
    private Long differenceResource;
    private Integer donation;
    private LocalDateTime registerDate;

}
