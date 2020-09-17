package br.com.discordBot.tributes.config;


import br.com.discordBot.tributes.entity.Gathering;
import br.com.discordBot.tributes.entity.MemberTribute;
import br.com.discordBot.tributes.service.MemberTributeService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@NoArgsConstructor
public class Start extends ListenerAdapter {

    MemberTributeService service;

    @Autowired
    public Start(MemberTributeService service) {
        this.service = service;
    }

    private static String AUTHOR = "";
    private static boolean PREFIX;
    private static String MESSAGE = "";

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        AUTHOR = event.getAuthor().getName().split(" ")[0];
        PREFIX = event.getMessage().getContentRaw().startsWith("/bot");
        MESSAGE = event.getMessage().getContentRaw().toLowerCase();

        List<MemberTribute> listMemberTribute;
        if (event.getAuthor().isBot()) return;
        if (PREFIX && MESSAGE.contains("tributos")) {

            event.getChannel().sendMessage("Olá " + AUTHOR + ", aguarde um pouco até a lista ser processada. ")
                    .queue();

            listMemberTribute = service.buildListOfTributes(false);
            LocalDateTime now = LocalDateTime.now();
            for (int i = 0; i < listMemberTribute.size(); i++) {

                Gathering gathering = listMemberTribute.get(i).getLifetimeStatistics().getGathering();

                event.getChannel().sendMessage("-------------------\n" + "Nome: " + listMemberTribute.get(i).getName() +
                        " | Fiber: " + gathering.getTotalFibe() +
                        " | Ore: " + gathering.getTotalOre() +
                        " | Wood: " + gathering.getTotalWood() +
                        " | Rock: " + gathering.getTotalRock() +
                        " | Hide: " + gathering.getTotalHide())
                        .queue();
            }
            System.out.println("Started at: " + now + " | Finished at: " + LocalDateTime.now());

        } else if (PREFIX && MESSAGE.contains("tributos") && MESSAGE.contains("atualizar")) {

            listMemberTribute = service.buildListOfTributes(true);

            event.getChannel().sendMessage("Sua lista foi atualizada, e possui " + listMemberTribute.size() +
                    " registros.");

        } else {
            event.getChannel().sendMessage(AUTHOR + ", não reconheci seu comando.").queue();
        }
    }

}


