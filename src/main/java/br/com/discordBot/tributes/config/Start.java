package br.com.discordBot.tributes.config;


import br.com.discordBot.tributes.entity.MemberTribute;
import br.com.discordBot.tributes.service.MemberTributeService;
import lombok.NoArgsConstructor;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@NoArgsConstructor
public class Start extends ListenerAdapter {

    MemberTributeService service;

    @Autowired
    public Start(MemberTributeService service) {
        this.service = service;
    }

    private static String AUTHOR = "";

//    public static void jdaBuilder () throws LoginException {
//        JDA jda = JDABuilder.createDefault("NzU0NDM2MTg2MjQzODU4NTgz.X10tcw.ysbomA4KHysWJtnOkU5PxMkSvgw").build();
//        jda.addEventListener(new Start());
//    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        AUTHOR = event.getAuthor().getName();
        if (event.getAuthor().isBot()) return;
        if (event.getMessage().getContentRaw().startsWith("/bot")) {
            if (event.getMessage().getContentRaw().toLowerCase().contains("tributos")) {

                event.getChannel().sendMessage("Eai " + AUTHOR + ", só de boas? ").queue();

                List<MemberTribute> listMemberTribute = service.buildListOfTributes();

                for (int i = 0; i < listMemberTribute.size(); i++) {
                    event.getChannel().sendMessage("-------------------\n" + "Nome: " + listMemberTribute.get(i).getName() +
                            "\nTotal Fiber: " + listMemberTribute.get(i).getLifetimeStatistics().getGathering().getTotalFibe() +
                            "\nTotal Ore: " + listMemberTribute.get(i).getLifetimeStatistics().getGathering().getTotalOre() +
                            "\n Total Wood: " + listMemberTribute.get(i).getLifetimeStatistics().getGathering().getTotalWood() +
                            "\n Total Rock: " + listMemberTribute.get(i).getLifetimeStatistics().getGathering().getTotalRock() +
                            "\n Total Hide: " + listMemberTribute.get(i).getLifetimeStatistics().getGathering().getTotalHide())
                            .queue();
                }

            } else {
                event.getChannel().sendMessage("Opa..  " + AUTHOR.split("")[0] + ", não reconheci seu comando." +
                        "\n tenta utilizar /bot antes! ").queue();
            }
        }

    }

}
