package pl.danielstrielnikow;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OtodomWebScrapper {

    private final String url = "https://www.otodom.pl/pl/wyniki/sprzedaz/mieszkanie/slaskie/gliwice/gliwice/gliwice?priceMax=500000&areaMax=50&viewType=listing";

    public List<Offer> getOffers() throws IOException {
        Document document = Jsoup.connect(url).get();

        List<String> links = getOffersLinksFromDocument(document)
                .subList(0, 15);

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

            String name = document.getElementsByAttributeValue("data-cy", "adPageAdTitle").get(0).text();
            String description = document.getElementsByAttributeValue("data-cy", "adPageAdDescription").get(0).text();
            String address = document.getElementsByAttributeValue("aria-label", "Adres").get(0).text();

            return Optional.of(new Offer(link, name, description, address));
        } catch (Exception e) {
            System.out.println("Error");
            return Optional.empty();
        }
    }
}

