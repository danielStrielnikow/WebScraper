package pl.danielstrielnikow;

import java.util.List;

public class OffersFilter {

    private final List<String> blockedWords = List.of("gazowe", "ogrzenia gazowe", "na gaz");

    private final List<String> isInAllowedNeighbourhoods = List.of("Sośnica");

    public List<Offer> filter(List<Offer> offers) {
        List<Offer> resultList = offers.stream()
                .filter(offer -> !offer.containsWords(blockedWords) && offer.containsWords(isInAllowedNeighbourhoods))
                .toList();

        if (offers.size() != resultList.size()) {
            System.out.println("Wyfiltrowaliśmy oferty! Było: " + offers.size() +
                    " a jest: " + resultList.size() + " ofert.");
        }

        return resultList;
    }
}
