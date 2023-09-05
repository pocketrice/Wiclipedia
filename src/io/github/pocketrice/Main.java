package io.github.pocketrice;

import org.jsoup.nodes.Element;

import javax.naming.InvalidNameException;
import java.io.IOException;
import java.util.Arrays;

import static io.github.pocketrice.AnsiCode.*;
import static io.github.pocketrice.WebScraper.*;

public class Main {
    public static void main(String[] args) throws IOException, InvalidNameException {
        long startTime = System.currentTimeMillis();
        System.out.println(ANSI_CYAN + "WiCli connecting...");
        WebScraper scraper = new WebScraper("https://en.wikipedia.org/wiki/Main_Page");

        long endTime = System.currentTimeMillis();
        System.out.println("WiCli connected! (" + (endTime - startTime) + "ms)" + ANSI_RESET);

        String topBanner = scraper.scrapeId(false, "mp-topbanner");
        System.out.println("\n\n\n\n" + topBanner.split("\\.")[0] + ".\n\t\t\t" + topBanner.split("\\.")[1] + "\n\n"); // bunch of hard-coded shenanigans for formatting
        System.out.println(ANSI_GREEN + "░░▒▒▓▒▒▓▓▒▒▓▓▓▓▓▓▓▓▓▓▓▓▓▒▓▓▓▒▒▒▓▒▓▒▒░░");

        System.out.println("From today's featured article\n");
        String tfa = scraper.scrapeId(false, "mp-tfa").replaceFirst(scraper.scrape(false, "#mp-tfa .thumbcaption"), ""); // p ugly code
        tfa = strWrap(tfa.substring(0, tfa.indexOf("Recently featured")), 90);// +  "\n\n" + strInsert(strUnwrap(tfa.substring(tfa.indexOf("Recently featured"))), "\n\n\t\t\t\t\t\t\t\t\t", "Archive");
        System.out.println(tfa);

        System.out.println("\n\n\n\n░░▒▒▓▒▒▓▓▒▒▓▓▓▓▓▓▓▓▓▓▓▓▓▒▓▓▓▒▒▒▓▒▓▒▒░░");
        System.out.println("Did you know...\n");
        Arrays.stream(scraper.scrapeId(false, "mp-dyk").replaceFirst(scraper.scrape(false, "#mp-dyk .thumbcaption") + "...", "").split("\\.\\.\\..")).map(s -> "* ..." + s + "\n").forEach(System.out::println);


        System.out.println(ANSI_BLUE + "\n\n\n\n░░▒▒▓▒▒▓▓▒▒▓▓▓▓▓▓▓▓▓▓▓▓▓▒▓▓▓▒▒▒▓▒▓▒▒░░");
        System.out.println("In the news\n");
        //scraper.doc.select("#mp-itn ul:nth-child(1) li").stream().map(e -> "* " + e.text() + "\n").forEach(System.out::println);
        scraper.doc.select("#mp-itn li").stream().filter(e -> e.text().split(" ").length > 5).map(e -> "* " + e.text() + "\n").forEach(System.out::println); // hard-coded
        System.out.println("\n" + strInsert(scraper.scrapeClass(false, "itn-footer"), "\n", "Recent deaths").replace("Nominate an article", ""));

        System.out.println("\n\n\n\n░░▒▒▓▒▒▓▓▒▒▓▓▓▓▓▓▓▓▓▓▓▓▓▒▓▓▓▒▒▒▓▒▓▒▒░░");
        System.out.println("On this day\n");
        System.out.println(scraper.scrapeId(false, "mp-otd p").replaceFirst(scraper.scrape(false, "#mp-otd .thumbcaption"), ""));
        System.out.println("\n\n");
        scraper.doc.select("#mp-otd li").stream().filter(e -> e.text().contains("–")).map(e -> "* " + e.text() + "\n").forEach(System.out::println);
        System.out.println("\n" + grammaticParse(scraper.doc.select("#mp-otd .hlist li").stream().map(Element::text).filter(text -> text.contains("(")).toArray(), "·", ""));



        System.out.println(ANSI_PURPLE + "\n\n\n\n░░▒▒▓▒▒▓▓▒▒▓▓▓▓▓▓▓▓▓▓▓▓▓▒▓▓▓▒▒▒▓▒▓▒▒░░");
        System.out.println("Today's featured picture\n");
        String tfp = scraper.scrapeId(false, "mp-tfp");
        String credit = scraper.doc.select("#mp-tfp small").first().text();
        tfp = strWrap(tfp.substring(0, tfp.indexOf(credit)), 90) + strInsert(strInsert(strUnwrap(tfp.substring(tfp.indexOf(credit))), "\n\n", credit), "\n\n", "Recently featured").replaceAll("Archive.*", "");
        System.out.println(tfp);
    }
}