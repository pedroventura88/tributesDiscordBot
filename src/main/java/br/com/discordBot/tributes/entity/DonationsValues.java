package br.com.discordBot.tributes.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DonationsValues {

    public Double typeItem;
    public Long quantityOfItens;

    public DonationsValues(Double typeItem, Long quantityOfItens) {
        this.typeItem = typeItem;
        this.quantityOfItens = quantityOfItens;
    }

    public static List<DonationsValues> populatingDonationValues() {
        List<DonationsValues> listValues = new ArrayList<>();
        DonationsValues d1 = new DonationsValues(6.0, 67L);
        DonationsValues d2 = new DonationsValues(6.1, 135L);
        DonationsValues d3 = new DonationsValues(6.2, 202L);
        DonationsValues d4 = new DonationsValues(6.3, 270L);
        DonationsValues d5 = new DonationsValues(7.0, 112L);
        DonationsValues d6 = new DonationsValues(7.1, 225L);
        DonationsValues d7 = new DonationsValues(7.2, 337L);
        DonationsValues d8 = new DonationsValues(7.3, 450L);
        DonationsValues d9 = new DonationsValues(8.0, 225L);
        DonationsValues d10 = new DonationsValues(8.1, 450L);
        DonationsValues d11 = new DonationsValues(8.2, 675L);
        DonationsValues d12 = new DonationsValues(8.3, 900L);
        listValues.add(d1);
        listValues.add(d2);
        listValues.add(d3);
        listValues.add(d4);
        listValues.add(d5);
        listValues.add(d6);
        listValues.add(d7);
        listValues.add(d8);
        listValues.add(d9);
        listValues.add(d10);
        listValues.add(d11);
        listValues.add(d12);
        return listValues;
    }

}


