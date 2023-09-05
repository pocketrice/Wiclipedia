package io.github.pocketrice;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.naming.InvalidNameException;
import java.io.IOException;
import java.util.Arrays;

public class WebScraper
{
   Document doc;


   public WebScraper() throws IOException {
       this("google.com");
   }

   public WebScraper(String u) throws IOException {
       setUri(u);
   }
    public void setUri(String uri) throws IOException {
        doc = Jsoup.connect(uri).get();
        doc.setBaseUri(uri);
    }

    public String scrapeClass(boolean isTextWrapped, String c, String... classes) throws IOException, InvalidNameException {
        //if (!validateName(c, classes)) throw new InvalidNameException();

        String query = "." + c;
        if (classes.length != 0) query += Arrays.stream(classes).map(s -> "." + s).reduce("", String::concat);
        return (isTextWrapped) ? strWrap(doc.select(query).text(), 90) : doc.select(query).text();
    }

    public String scrapeId(boolean isTextWrapped, String i, String... ids) throws IOException, InvalidNameException {
        //if (!validateName(i, ids)) throw new InvalidNameException();

        String query = "#" + i;
        if (ids.length != 0) query += Arrays.stream(ids).map(s -> "#" + s).reduce("", String::concat);
        return (isTextWrapped) ? strWrap(doc.select(query).text(), 90) : doc.select(query).text();
    }

    public String scrapeTag(boolean isTextWrapped, String t, String... tags) throws IOException, InvalidNameException {
        //if (!validateName(t, tags)) throw new InvalidNameException();
        // todo: patch t in; I wanted to keep grammaticparse for clout lmao
        String query = grammaticParse(tags, ",", "");
        return (isTextWrapped) ? strWrap(doc.select(query).text(), 90) : doc.select(query).text();
    }

    // Validate CSS naming conventions
  //public boolean validateName(String name, String... names) { // https://rgxdb.com/r/3SSUL9QL
   //    return name.matches("//-?[_a-zA-Z]+[_a-zA-Z0-9-]*//") && Arrays.stream(names).allMatch(s -> s.matches("//-?[_a-zA-Z]+[_a-zA-Z0-9-]*//"));
   // }

    // General-use scrape
    public String scrape(boolean isTextWrapped, String query) throws IOException {
       return (isTextWrapped) ? strWrap(doc.select(query).text(), 90) : doc.select(query).text();
    }

    // APM
    public static String strWrap(String str, int twc) {
       StringBuilder strb = new StringBuilder(str);
        for (int i = 0; i < str.length(); i += twc) strb.insert(i, "\n");
        return strb.toString();
    }

    public static String strUnwrap(String str) {
       return str.replaceAll("\\n", "");
    }

    public static String strInsert(String str, String ins, String spl) {
        // ins = what to insert, spl = split point
        return str.substring(0, str.indexOf(spl)) + ins + str.substring(str.indexOf(spl));
    }

    // APM
    public static <T> String grammaticParse(T[] array, String sep, String conjunction) { // <+> APM
        StringBuilder gParsedString = new StringBuilder(Arrays.toString(array));

        gParsedString.deleteCharAt(0)
                .deleteCharAt(gParsedString.length()-1);

        if (array.length > 1 && !conjunction.equals("")) gParsedString.insert(gParsedString.lastIndexOf(",") + 1, " " + conjunction);

        return gParsedString.toString().replaceAll(",", sep);
    }
}