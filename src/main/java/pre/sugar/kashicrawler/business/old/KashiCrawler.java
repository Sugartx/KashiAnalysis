package pre.sugar.kashicrawler.business.old;

import com.google.common.collect.Lists;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import pre.sugar.kashicrawler.entity.FuzzyTypes;
import pre.sugar.kashicrawler.entity.SelectTypes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: Xiao Tang
 * Date: 2022/6/23 9:54
 * Description: \
 */
public class KashiCrawler {

    private static final String BASE_URL = "https://www.uta-net.com";
    private static final String SEARCH_PATH = "/search/?";

    public List<String> getKashiList(SelectTypes select, String keyword, FuzzyTypes fuzzy) {
        List<String> kashiUrlList = getKashiUrlList(select, keyword, fuzzy);
        return kashiUrlList.stream().flatMap(url -> getKashi(url).stream()).collect(Collectors.toList());
    }

    private List<String> getKashi(String kashiUrl) {
        try {
            URL url = new URL(BASE_URL + kashiUrl);
            Document doc = Jsoup.parse(url, 10000);
            return doc.getElementById("kashi_area").textNodes().stream().map(TextNode::text).collect(Collectors.toList());
        } catch (IOException e) {
            return null;
        }
    }

    private List<String> getKashiUrlList(SelectTypes select, String keyword, FuzzyTypes fuzzy) {
        URL url = buildUrl(select, keyword, fuzzy);
        if (url == null) {
            return Collections.emptyList();
        }
        List<String> kashiUrlList = Lists.newArrayList();
        try {
            Document doc = Jsoup.parse(url, 10000);
            Element element = doc.getElementById("ichiran");
            Element table = element.child(2).child(0).child(1);
            for (Element row : table.children()) {
                String kashiUrl = row.child(0).child(0).attr("href");
                kashiUrlList.add(kashiUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return kashiUrlList;
    }

    private URL buildUrl(SelectTypes select, String keyword, FuzzyTypes fuzzy) {
        StringBuilder sb = new StringBuilder();
        sb.append(BASE_URL)
                .append(SEARCH_PATH)
                .append("Aselect=")
                .append(select.getVal())
                .append("&Keyword=")
                .append(keyword)
                .append("&Bselect=")
                .append(fuzzy.getVal());
        try {
            return new URL(sb.toString());
        } catch (MalformedURLException e) {
            return null;
        }
    }

//    public static void main(String[] args) {
//        KashiCrawler kashiCrawler = new KashiCrawler();
//        List<String> kachiList = kashiCrawler.getKashiList(SelectTypes.ARTIST, "ねこぼーろ", FuzzyTypes.CONTAIN);
//        WordFrequencyAnalysis analysis = new WordFrequencyAnalysis(2, 5, 5);
//        Map<Integer, List<WordFrequency>> resultMap = Maps.newHashMap();
//        for (String row : kachiList) {
//            resultMap.putAll(analysis.analysis(row));
//        }
//        WordFrequencyAnalysis.print(resultMap);
//    }
}
