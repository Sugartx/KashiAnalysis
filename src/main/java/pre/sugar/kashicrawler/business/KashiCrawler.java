package pre.sugar.kashicrawler.business;

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
import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: Xiao Tang
 * Date: 2022/6/23 12:27
 * Description: \
 */
public class KashiCrawler {
    private static final int TIMEOUT = 5000;

    private static final String BASE_URL = "https://www.uta-net.com";
    private static final String SEARCH_PATH = "/search/?";

    public List<String> getKashiList(SelectTypes select, String keyword, FuzzyTypes fuzzy){
        List<String> kashiUrlList = getKashiUrlList(select, keyword, fuzzy);
        return kashiUrlList.stream().flatMap(url-> getKashi(url).stream()).collect(Collectors.toList());
    }

    private List<String> getKashi(String kashiUrl){
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
        if (url == null){
            return Lists.newArrayList();
        }
        List<String> kashiUrlList = Lists.newArrayList();
        while (true) {
            try {
                Document doc = Jsoup.parse(url, TIMEOUT);
                String artistUrl = doc.getElementsByClass("d-block").attr("href");
                doc = Jsoup.parse(new URL(BASE_URL + artistUrl), TIMEOUT);
                Element table = doc.getElementsByClass("songlist-table-body").first();
                for (Element row : table.children()) {
                    String kashiUrl = row.child(0).child(0).attr("href");
                    kashiUrlList.add(kashiUrl);
                }
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Song count:"+kashiUrlList.size());
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

}
