package pl.danielstrielnikow;

import java.util.List;

public record Offer(String url, String name, String description, String address) {

    public boolean containsBlockedWords(List<String> blockedWords) {
        return blockedWords.stream()
                .map(String::toLowerCase)
                .anyMatch(blockedWord ->
                        name.toLowerCase().contains(blockedWord) ||
                                description.toLowerCase().contains(blockedWord) ||
                                address.toLowerCase().contains(blockedWord));
    }

    public boolean isInAllowedNeighbourhoods(List<String> allowedNeighbourhoods) {
        return allowedNeighbourhoods.stream()
                .map(String::toLowerCase)
                .anyMatch(neighbourhood -> address.toLowerCase().contains(neighbourhood));
    }
}
