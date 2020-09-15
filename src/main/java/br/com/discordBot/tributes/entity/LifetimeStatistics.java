package br.com.discordBot.tributes.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
public class LifetimeStatistics implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LIFETIMESTATISTICS")
    private Long idLifetimeStatistics;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_GATHERING")
    private Gathering gathering;

    @Column(name = "TIMESTAMP_LIFETIMESTATISTICS")
    private LocalDateTime timestamp;

    public LifetimeStatistics(Gathering gathering, LocalDateTime timestamp) {
        this.gathering = gathering;
        this.timestamp = timestamp;
    }

    public LifetimeStatistics() {
    }
}
