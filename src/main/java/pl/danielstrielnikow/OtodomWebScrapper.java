package pl.danielstrielnikow;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.jsoup.nodes.Element;
public class OtodomWebScrapper {

    private final String url = "https://www.otodom.pl/pl/wyniki/sprzedaz/mieszkanie/slaskie/gliwice/gliwice/gliwice?limit=36&ownerTypeSingleSelect=ALL&priceMax=500000&areaMax=50&by=DEFAULT&direction=DESC&viewType=listing";
    final int SIZE = 20;
    public List<Offer> getOffers() throws IOException {
        Document document = Jsoup.connect(url).get();

        List<String> links = getOffersLinksFromDocument(document)
                .subList(0, SIZE);
        if (links.isEmpty()) {
            System.out.println("Brak ofert do przetworzenia.");
            return List.of(); // lub throw new NoSuchElementException("No offers found.");
        }

        return links.stream()
                .map(this::createOffering)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private static List<String> getOffersLinksFromDocument(Document document) {
        return document.getElementsByAttributeValue("data-cy", "listing-item-link").stream()
                .map(e -> e.attr("href"))
                .map(href -> "https://www.otodom.pl" + href)
                .toList();
    }

    private Optional<Offer> createOffering(String link) {
        try {
            Thread.sleep(10);
            System.out.println("Getting offering: " + link);
            Document document = Jsoup.connect(link).get();

            // Uzyskiwanie tytułu
            Element titleElement = document.getElementsByAttributeValue("data-cy", "adPageAdTitle").first();
            String name = (titleElement != null) ? titleElement.text() : "Brak tytułu";

            // Uzyskiwanie opisu
            Element descriptionElement = document.getElementsByAttributeValue("data-cy", "adPageAdDescription").first();
            String description = (descriptionElement != null) ? descriptionElement.text() : "Brak opisu";

            // Uzyskiwanie adresu
            Element addressElement = document.getElementsByAttributeValue("aria-label", "Adres").first();
            String address = (addressElement != null) ? addressElement.text() : "Brak adresu";

            return Optional.of(new Offer(link, name, description, address));
        } catch (Exception e) {
            System.out.println("Error while getting offering: " + link + " - " + e.getMessage());
            return Optional.empty();
        }
    }


}


