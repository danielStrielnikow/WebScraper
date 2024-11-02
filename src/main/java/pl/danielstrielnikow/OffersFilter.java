package pl.danielstrielnikow;

import java.util.List;

public class OffersFilter {

    private final List<String> blockedWords = List.of("ogrzewanie własne", "ogrzewanie elektryczne",
            "ogrzewania elektronicznego", "ogrzewaniem", "ogrzenia gazowe");

    private final List<String> allowedNeighbourhoods = List.of("Sośnica", "sośnica");

    public List<Offer> filter(List<Offer> offers) {
        List<Offer> resultList = offers.stream()
                .filter(offer -> !offer.containsBlockedWords(blockedWords) && offer.isInAllowedNeighbourhoods(allowedNeighbourhoods))
                .toList();

        if (offers.size() != resultList.size()) {
            System.out.println("Wyfiltrowaliśmy oferty! Było: " + offers.size() +
                    " a jest: " + resultList.size() + " ofert.");
        }

        return resultList;
    }
}
