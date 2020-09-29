package br.com.discordBot.tributes.config;


import br.com.discordBot.tributes.entity.Donations;
import br.com.discordBot.tributes.entity.MemberTribute;
import br.com.discordBot.tributes.service.DonationsService;
import br.com.discordBot.tributes.service.FirstRegisterService;
import br.com.discordBot.tributes.service.MemberTributeService;
import br.com.discordBot.tributes.util.CreateFileUtils;
import br.com.discordBot.tributes.util.DataUtils;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
@NoArgsConstructor
public class Start extends ListenerAdapter {

    MemberTributeService service;

    FirstRegisterService firstRegisterService;

    DonationsService donationsService;

    JSONArray jsonArrayInMemory;

    @Autowired
    public Start(MemberTributeService service, FirstRegisterService firstRegisterService, DonationsService donationsService) {
        this.service = service;
        this.firstRegisterService = firstRegisterService;
        this.donationsService = donationsService;
    }

    private static String AUTHOR = "";
    private static boolean PREFIX;
    private static String MESSAGE = "";

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        AUTHOR = event.getAuthor().getName().split(" ")[0];
        PREFIX = event.getMessage().getContentRaw().startsWith("/bot");
        MESSAGE = event.getMessage().getContentRaw().toLowerCase();

        try {
            jsonArrayInMemory = new JSONArray(ConectaApiAlbion.loadTributes());
        } catch (UnirestException e) {
            event.getChannel().sendMessage("Ops.. a API do Albion não está respondendo.").queue();
            log.error("Failed to load API data.");
        }

        List<MemberTribute> listMemberTribute;
        List<List<List<Donations>>> listDonations;
        if (event.getAuthor().isBot()) return;

        if (PREFIX && MESSAGE.contains("primeiro insert")) {
            isFirstInsert(event);
        }

        if (PREFIX && MESSAGE.contains("html")) {
            CreateFileUtils cfu = new CreateFileUtils();
            try {

                File file = cfu.writeToFile(cfu.openingHtmlFile(), cfu.createHtmlBody(), cfu.closingHtmlFile(),
                        "doacoes" + DataUtils.convertLocalDateToDateBr(LocalDateTime.now()) + ".html");

                event.getChannel()
                        .sendMessage("Eis aqui teu arquivo: ").addFile(file).queue();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (PREFIX && MESSAGE.contains("tributos") && MESSAGE.contains("atualizar")) {

            try {
                service.buildListOfTributes(true, jsonArrayInMemory);
                event.getChannel().sendMessage("Sua lista foi atualizada").queue();
            } catch (NoResultException nre) {
                event.getChannel().sendMessage(AUTHOR + ", " + nre.getMessage()).queue();
                log.warn("There's something missing here: ", nre.getMessage());
            } catch (Exception e) {
                log.error("Ops.. there is an error while trying to recover the list of Tributes - UPDATE METHOD: ",
                        e.getMessage());
            }

        } else if (PREFIX && MESSAGE.contains("tributos")) {

            event.getChannel().sendMessage("Olá " + AUTHOR + ", aguarde um pouco até a lista ser processada. " +
                    "Iniciando: " + DataUtils.convertLocalDateTimeToBRFull(LocalDateTime.now()))
                    .queue();

            try {

                listMemberTribute = service.buildListOfTributes(false, jsonArrayInMemory);

                listDonations = donationsService.buildListOfDonations(jsonArrayInMemory);

                if (listDonations.size() > 0) {
                    if (MESSAGE.contains("donations")) {
                        System.out.println("[ ");
                        for (List<List<Donations>> list : listDonations) {
                            for (List<Donations> d : list) {
                                for (Donations donations : d) {
                                    System.out.println("MEMBRO: " + donations.getMemberName() + " | RECURSO: " + donations.getResourceName() + " | TIPO ITEM: " + donations.getTypeItem() + " | VALOR DOADO: " + donations.getDonation());
                                }

                            }
                            System.out.println("], ");
                        }
                        System.out.println("]");
                    }

                    //            for (int i = 0; i < listMemberTribute.size(); i++) {
//
//                Gathering gathering = listMemberTribute.get(i).getLifetimeStatistics().getGathering();
//
//                event.getChannel().sendMessage("-------------------\n" + "Nome: " + listMemberTribute.get(i).getName() +
//                        " | Fiber: " + gathering.getTotalFibe() +
//                        " | Ore: " + gathering.getTotalOre() +
//                        " | Wood: " + gathering.getTotalWood() +
//                        " | Rock: " + gathering.getTotalRock() +
//                        " | Hide: " + gathering.getTotalHide())
//                        .queue();
//            }

                    event.getChannel().sendMessage("Finalizado: " + DataUtils
                            .convertLocalDateTimeToBRFull(LocalDateTime.now())).queue();
                } else {
                    event.getChannel().sendMessage("Não há registros a serem lançados").queue();
                }
            } catch (NoResultException nre) {
                event.getChannel().sendMessage("OPS.. " + nre.getMessage()).queue();
                log.warn("There's something missing here: ", nre.getStackTrace());
            } catch (Exception e) {
                log.error("Ops.. there is an error while trying to recover the list of Tributes ",
                        e.getMessage());
            }

        } else if (PREFIX && (!MESSAGE.contains("tributos")) && (!MESSAGE.contains("atualizar"))
                && (!MESSAGE.contains("primeiro insert")) && (!MESSAGE.contains("html"))) {
            event.getChannel().sendMessage(AUTHOR + ", não reconheci seu comando.").queue();
        }
    }

    private void isFirstInsert(MessageReceivedEvent event) {
        List<MemberTribute> firstInsertMembers = null;
        try {
            firstInsertMembers = firstRegisterService.buildFirstInsertMembers(jsonArrayInMemory);
            event.getChannel().sendMessage("Foram inseridos " + firstInsertMembers.size() + " novos membros.").queue();
        } catch (Exception exception) {
            event.getChannel()
                    .sendMessage("Esse comando não pode ser executado pois já existem registros no banco de dados")
                    .queue();
        }
    }

}


