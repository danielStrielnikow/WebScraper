package pl.danielstrielnikow;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        OtodomWebScrapper scraper = new OtodomWebScrapper();
        OffersFilter offersFilter = new OffersFilter();

        List<Offer> offers = scraper.getOffers();

        List<Offer> filtered = offersFilter.filter(offers);

        System.out.println("Przefiltrowane ofery:");
        filtered.stream()
                .map(o -> o.url() + " " + o.address())
                .forEach(System.out::println);

    }
}