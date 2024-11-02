package pl.danielstrielnikow;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        OtodomWebScrapper scraper = new OtodomWebScrapper();
        OffersFilter offersFilter = new OffersFilter();

        List<Offer> offers = scraper.getOffers();

        System.out.println("Nieprzefiltrowane:");
        offers.forEach(System.out::println);

        List<Offer> filtered = offersFilter.filter(offers);

        System.out.println("Przefiltrowane ofery:");
        filtered.forEach(System.out::println);

    }
}