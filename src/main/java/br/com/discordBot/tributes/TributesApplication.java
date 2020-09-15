package br.com.discordBot.tributes;

import br.com.discordBot.tributes.config.Start;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.security.auth.login.LoginException;

@SpringBootApplication
public class TributesApplication implements CommandLineRunner {

    @Autowired
    private Start inicio;

    public static void main(String[] args) {
        SpringApplication.run(TributesApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            JDA jda = JDABuilder.createDefault("NzU0NDM2MTg2MjQzODU4NTgz.X10tcw.ysbomA4KHysWJtnOkU5PxMkSvgw").build();
            jda.addEventListener(inicio);
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
