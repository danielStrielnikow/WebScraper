package pl.danielstrielnikow;

import java.util.List;
import java.util.Objects;

public record Offer(String url, String name, String description, String address) {

    public boolean containsWords(List<String> blockedWords) {
        return blockedWords.stream()
                .map(String::toLowerCase)
                .anyMatch(blockedWord ->
                        name.toLowerCase().contains(blockedWord) ||
                                description.toLowerCase().contains(blockedWord) ||
                                address.toLowerCase().contains(blockedWord));
    }

//    public boolean isInAllowedNeighbourhoods(List<String> allowedNeighbourhoods) {
//        return allowedNeighbourhoods.stream()
//                .map(String::toLowerCase)
//                .anyMatch(neighbourhood -> address.toLowerCase().contains(neighbourhood));
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return Objects.equals(url, offer.url);  // Porównujemy po URL, zakładając, że URL jest unikalny
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);  // Generujemy hashCode na podstawie URL
    }
}

