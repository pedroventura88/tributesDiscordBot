package br.com.discordBot.tributes.service;

import br.com.discordBot.tributes.entity.*;
import br.com.discordBot.tributes.repository.MemberTributeRepository;
import br.com.discordBot.tributes.util.DataUtils;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DonationsService {

    MemberTributeRepository memberTributeRepository;

    @Autowired
    private LifetimeStatisticsService lifetimeStatisticsService;

    @Autowired
    private GatheringService gatheringService;

    @Autowired
    private MemberTributeService memberTributeService;

    @Autowired
    public DonationsService(MemberTributeRepository memberTributeRepository) {
        this.memberTributeRepository = memberTributeRepository;
    }

    public List<List<List<Donations>>> buildListOfDonations(JSONArray jsonArrayInMemory) throws UnirestException {

        List<MemberTribute> listOfMemberTribute = new ArrayList<>();

        List<List<Donations>> donations = new ArrayList<>();
        List<List<List<Donations>>> donationsFinal = new ArrayList<>();

        for (int i = 0; i < jsonArrayInMemory.length(); i++) {

            /**Construindo Objeto Gathering **/
            Gathering gathering = gatheringService.buildingGatheringTributes(i, jsonArrayInMemory, false);

            /** Construindo objeto Lifetime **/
            LifetimeStatistics lifetimeStatistics = lifetimeStatisticsService.buildingLifetimeStatistics(gathering, false);

            /** Construindo objeto Member  **/
            MemberTribute member = memberTributeService.getMemberTribute(jsonArrayInMemory, i, lifetimeStatistics, false);

            List<MemberTribute> actualTributesList;
            List<MemberTribute> lastWeekTributesList;

            actualTributesList = memberTributeRepository.findMemberTributeById(member.getId(),
                    DataUtils.convertLocalDateToString(LocalDate.now().minusDays(1)));

            lastWeekTributesList = memberTributeRepository.findMemberTributeById(member.getId(),
                    DataUtils.convertLocalDateToString(LocalDate.now().minusDays(2)));

            donations = creatingDonations(actualTributesList, lastWeekTributesList);
            donationsFinal.add(donations);


            /** OPS.. Qual lista passar aqui?? acho que deve ser doações..**/
            listOfMemberTribute.add(member);

        }

        return donationsFinal;
    }


    public List<List<Donations>> creatingDonations(List<MemberTribute> actualTributesList,
                                                   List<MemberTribute> lastWeekTributesList) {

        List<DonationsValues> donationsValues = DonationsValues.populatingDonationValues();

        List<List<Donations>> donationsList = new ArrayList<>();

        if (lastWeekTributesList.size() > 0) {

            for (MemberTribute atual : actualTributesList) {

                for (MemberTribute lastWeek : lastWeekTributesList) {

                    Long difference;

                    if (lastWeek.getId().equals(atual.getId())) {

                        if (lastWeek.getLifetimeStatistics().getGathering().getTotalFibe() <
                                atual.getLifetimeStatistics().getGathering().getTotalFibe()) {

                            List<Donations> donationsFibeList = new ArrayList<>();

                            difference = atual.getLifetimeStatistics().getGathering().getTotalFibe()
                                    - lastWeek.getLifetimeStatistics().getGathering().getTotalFibe();

                            System.out.println("USER: " + atual.getName() + " | DIFFERENCE FIBER: " + difference);

                            iteratingDonationsValues(donationsValues, difference, donationsFibeList, "Fiber", atual);

                            donationsList.add(donationsFibeList);

                        }

                        if (lastWeek.getLifetimeStatistics().getGathering().getTotalHide() <
                                atual.getLifetimeStatistics().getGathering().getTotalHide()) {

                            List<Donations> donationsHideList = new ArrayList<>();

                            difference = atual.getLifetimeStatistics().getGathering().getTotalHide()
                                    - lastWeek.getLifetimeStatistics().getGathering().getTotalHide();
                            System.out.println("USER: " + atual.getName() + " | DIFFERENCE HIDE: " + difference);

                            iteratingDonationsValues(donationsValues, difference, donationsHideList, "Hide", atual);

                            donationsList.add(donationsHideList);

                        }

                        if (lastWeek.getLifetimeStatistics().getGathering().getTotalOre() <
                                atual.getLifetimeStatistics().getGathering().getTotalOre()) {

                            List<Donations> donationsOreList = new ArrayList<>();

                            difference = atual.getLifetimeStatistics().getGathering().getTotalOre()
                                    - lastWeek.getLifetimeStatistics().getGathering().getTotalOre();
                            System.out.println("USER: " + atual.getName() + " | DIFFERENCE ORE: " + difference);

                            iteratingDonationsValues(donationsValues, difference, donationsOreList, "Ore", atual);

                            donationsList.add(donationsOreList);

                        }

                        if (lastWeek.getLifetimeStatistics().getGathering().getTotalRock() <
                                atual.getLifetimeStatistics().getGathering().getTotalRock()) {

                            List<Donations> donationsRockList = new ArrayList<>();

                            difference = atual.getLifetimeStatistics().getGathering().getTotalRock()
                                    - lastWeek.getLifetimeStatistics().getGathering().getTotalRock();
                            System.out.println("USER: " + atual.getName() + " | DIFFERENCE ROCK: " + difference);

                            iteratingDonationsValues(donationsValues, difference, donationsRockList, "Rock", atual);

                            donationsList.add(donationsRockList);

                        }

                        if (lastWeek.getLifetimeStatistics().getGathering().getTotalWood() <
                                atual.getLifetimeStatistics().getGathering().getTotalWood()) {

                            List<Donations> donationsWoodList = new ArrayList<>();

                            difference = atual.getLifetimeStatistics().getGathering().getTotalWood()
                                    - lastWeek.getLifetimeStatistics().getGathering().getTotalWood();
                            System.out.println("USER: " + atual.getName() + " | DIFFERENCE WOOD: " + difference);

                            iteratingDonationsValues(donationsValues, difference, donationsWoodList, "Wood", atual);

                            donationsList.add(donationsWoodList);

                        }

                    }

                }

            }

        }

        return donationsList;
    }

    private void iteratingDonationsValues(List<DonationsValues> donationsValues, Long difference,
                                          List<Donations> donationsList, String resourceName, MemberTribute member) {
        for (DonationsValues value : donationsValues) {
            Donations genericDonation = new Donations();
            genericDonation.setMemberName(member.getName());
            genericDonation.setMemberId(member.getIdMember());
            genericDonation.setResourceName(resourceName);
            genericDonation.setDonation(Integer.valueOf((int) (difference / value.getQuantityOfItens())));
            genericDonation.setTypeItem(value.getTypeItem());
            genericDonation.setQuantityOfItens(value.getQuantityOfItens());
            genericDonation.setRegisterDate(LocalDateTime.now());
            if (genericDonation.getDonation() > 0) {
                donationsList.add(genericDonation);
            }

        }

    }

}
