package com.soundrecords.amazinprices;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AmazonPricesTest {

    @Test
    public void getAmazonPrices() throws IOException {
        List<String> asins;
        try (Stream<String> lines = Files.lines(Paths.get("src/test/resources/asins.txt"))) {
            asins = lines.collect(Collectors.toList());
        }
        Playwright playwright = Playwright.create();
        Browser browser = playwright.webkit().launch();
        Page page = browser.newPage();
        try {
            for (String asin : asins) {

                page.navigate("https://www.amazon.co.uk/dp/" + asin);
                String priceWhole = page.locator("xpath=" +
                        "//*[@id=\"corePriceDisplay_desktop_feature_div\"]/div[1]/span[1]"
                ).first().textContent();
                // Print the extracted price
                System.out.println(asin + "," + priceWhole);
            }
        } catch (Exception e) {

        }
    }
}
