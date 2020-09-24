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
    private String resourceName;
    private Long differenceResource;
    private Integer donation;
    private Double typeItem;
    private Long quantityOfItens;
    private LocalDateTime registerDate;

}
