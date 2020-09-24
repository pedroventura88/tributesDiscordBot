package br.com.discordBot.tributes.service;

import br.com.discordBot.tributes.entity.Donations;
import br.com.discordBot.tributes.entity.DonationsValues;
import br.com.discordBot.tributes.entity.Gathering;
import br.com.discordBot.tributes.entity.MemberTribute;
import br.com.discordBot.tributes.util.DataUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class DonationsService {

    public List<Donations> creatingDonations(List<MemberTribute> actualTributesList,
                                             List<MemberTribute> lastWeekTributesList) {

        List<DonationsValues> donationsValues = DonationsValues.populatingDonationValues();

        if (lastWeekTributesList.size() > 0) {

            for (MemberTribute atual : actualTributesList) {

                for (MemberTribute lastWeek : lastWeekTributesList) {

                    List<List<Donations>> donationsList = new ArrayList<>();

                    Long difference;

                    if (lastWeek.getId().equals(atual.getId())) {

                        if (lastWeek.getLifetimeStatistics().getGathering().getTotalFibe() <
                                atual.getLifetimeStatistics().getGathering().getTotalFibe()) {

                            List<Donations> donationsFibeList = new ArrayList<>();

                            difference = atual.getLifetimeStatistics().getGathering().getTotalFibe()
                                    - lastWeek.getLifetimeStatistics().getGathering().getTotalFibe();

                            System.out.println("USER: " + atual.getName() + " | DIFFERENCE FIBER: " + difference);

                            iteratingDonationsValues(donationsValues, difference, donationsFibeList, "Fiber");

                            donationsList.add(donationsFibeList);

                        }

                        if (lastWeek.getLifetimeStatistics().getGathering().getTotalHide() <
                                atual.getLifetimeStatistics().getGathering().getTotalHide()) {

                            List<Donations> donationsHideList = new ArrayList<>();

                            difference = atual.getLifetimeStatistics().getGathering().getTotalHide()
                                    - lastWeek.getLifetimeStatistics().getGathering().getTotalHide();
                            System.out.println("USER: " + atual.getName() + " | DIFFERENCE HIDE: " + difference);

                            iteratingDonationsValues(donationsValues, difference, donationsHideList, "Hide");

                            donationsList.add(donationsHideList);

                        }

                        if (lastWeek.getLifetimeStatistics().getGathering().getTotalOre() <
                                atual.getLifetimeStatistics().getGathering().getTotalOre()) {

                            List<Donations> donationsOreList = new ArrayList<>();

                            difference = atual.getLifetimeStatistics().getGathering().getTotalOre()
                                    - lastWeek.getLifetimeStatistics().getGathering().getTotalOre();
                            System.out.println("USER: " + atual.getName() + " | DIFFERENCE ORE: " + difference);

                            iteratingDonationsValues(donationsValues, difference, donationsOreList,"Ore");

                            donationsList.add(donationsOreList);

                        }

                        if (lastWeek.getLifetimeStatistics().getGathering().getTotalRock() <
                                atual.getLifetimeStatistics().getGathering().getTotalRock()) {

                            List<Donations> donationsRockList = new ArrayList<>();

                            difference = atual.getLifetimeStatistics().getGathering().getTotalRock()
                                    - lastWeek.getLifetimeStatistics().getGathering().getTotalRock();
                            System.out.println("USER: " + atual.getName() + " | DIFFERENCE ROCK: " + difference);

                            iteratingDonationsValues(donationsValues, difference, donationsRockList, "Rock");

                            donationsList.add(donationsRockList);

                        }

                        if (lastWeek.getLifetimeStatistics().getGathering().getTotalWood() <
                                atual.getLifetimeStatistics().getGathering().getTotalWood()) {

                            List<Donations> donationsWoodList = new ArrayList<>();

                            difference = atual.getLifetimeStatistics().getGathering().getTotalWood()
                                    - lastWeek.getLifetimeStatistics().getGathering().getTotalWood();
                            System.out.println("USER: " + atual.getName() + " | DIFFERENCE WOOD: " + difference);

                            iteratingDonationsValues(donationsValues, difference, donationsWoodList, "Wood");

                            donationsList.add(donationsWoodList);

                        }


                    }

                }

            }

        } else if (lastWeekTributesList.size() == 0 && actualTributesList.size() == 0) {
            throw new NoResultException("a API não possui dados nos dias "
                    + DataUtils.convertLocalDateToDateBr(LocalDateTime.now().minusDays(1)) + " e "
                    + DataUtils.convertLocalDateToDateBr(LocalDateTime.now()));

        } else if (lastWeekTributesList.size() == 0) {
            throw new NoResultException("falha ao recuperar informações da ultima semana.\nA API não possui dados no dia "
                    + DataUtils.convertLocalDateToDateBr(LocalDateTime.now().minusDays(1)) + ".");

        } else if (actualTributesList.size() == 0) {
            throw new NoResultException("falha ao recuperar informações de hoje!.\nA API não possui dados no dia "
                    + DataUtils.convertLocalDateToDateBr(LocalDateTime.now()) + ".");
        }

        return null;
    }

    private void iteratingDonationsValues(List<DonationsValues> donationsValues, Long difference,
                                          List<Donations> donationsList, String resourceName) {
        for (DonationsValues value : donationsValues) {
            Donations genericDonation = new Donations();
            genericDonation.setResourceName(resourceName);
            genericDonation.setDonation(Integer.valueOf((int) (difference / value.getQuantityOfItens())));
            genericDonation.setTypeItem(value.getTypeItem());
            genericDonation.setQuantityOfItens(value.getQuantityOfItens());
            donationsList.add(genericDonation);
        }

    }

}
