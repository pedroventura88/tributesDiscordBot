package br.com.discordBot.tributes;

import br.com.discordBot.tributes.config.Start;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.security.auth.login.LoginException;

@Slf4j
@SpringBootApplication
public class TributesApplication implements CommandLineRunner {

    @Autowired
    private Start start;

    public static void main(String[] args) {
        SpringApplication.run(TributesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
//            JDA jda = JDABuilder.createDefault("xxxxxxxxxxxxxxxxxxxTOKENxxxxxxxxxxxxxxxxxxxx").build();
            JDA jda = JDABuilder.createDefault("NzU0NDM2MTg2MjQzODU4NTgz.X10tcw.LfseWun94nwp1RWYKdaRem-CCSs").build();
            jda.addEventListener(start);
        } catch (LoginException e) {
            log.error(" ## Failed to login using specified credentials: ", e);
        } catch (Exception e) {
            log.error(" ## Failed to build JDA Object (main class): ", e);
        }
    }
}
