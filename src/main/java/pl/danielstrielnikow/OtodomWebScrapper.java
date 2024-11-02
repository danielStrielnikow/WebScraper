package pl.danielstrielnikow;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.jsoup.nodes.Element;

public class OtodomWebScrapper {

    private final String url = "https://www.otodom.pl/pl/wyniki/sprzedaz/mieszkanie/slaskie/gliwice/gliwice/gliwice/sosnica?limit=36&ownerTypeSingleSelect=ALL&by=DEFAULT&direction=DESC&viewType=listing";
    final int MAX_PAGES = 10; // Maksymalna liczba stron do przetworzenia

    public List<Offer> getOffers() throws IOException {
        List<String> allLinks = new ArrayList<>();
        int page = 1;


        while (page <= MAX_PAGES) {
            Document document = Jsoup.connect(url + "&page=" + page).get();
            List<String> links = getOffersLinksFromDocument(document);

            if (links.isEmpty()) {
                break; // Brak więcej ofert
            }

            allLinks.addAll(links);
            page++; // Przechodzimy do następnej strony
        }

        // Ostatecznie ograniczamy do 30 ofert lub jakiejkolwiek innej liczby
        return allLinks.stream()
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
            Element addressElement = document.select("a.css-1jjm9oe.e42rcgs1").first();
            String address = (addressElement != null) ? addressElement.text() : "Brak adresu";

            return Optional.of(new Offer(link, name, description, address));
        } catch (Exception e) {
            System.out.println("Error while getting offering: " + link + " - " + e.getMessage());
            return Optional.empty();
        }
    }


}


