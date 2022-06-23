package pre.sugar.kashicrawler;

import org.junit.jupiter.api.Test;
import pre.sugar.kashicrawler.business.KashiCrawler;
import pre.sugar.kashicrawler.business.WordFrequencyAnalysis;
import pre.sugar.kashicrawler.entity.FuzzyTypes;
import pre.sugar.kashicrawler.entity.SelectTypes;
import pre.sugar.kashicrawler.entity.WordFrequency;

import java.util.List;
import java.util.Map;

/**
 * Author: Xiao Tang
 * Date: 2022/6/23 16:27
 * Description: \
 */


public class KashiCrawlerTest {
    @Test
    public void test(){
        KashiCrawler kashiCrawler = new KashiCrawler();
        List<String> kachiList = kashiCrawler.getKashiList(SelectTypes.LYRIC, "ねこぼーろ", FuzzyTypes.CONTAIN);
        WordFrequencyAnalysis analysis = new WordFrequencyAnalysis(2,5, 3);
        Map<Integer, List<WordFrequency>> resultMap = analysis.analysis(kachiList);
        WordFrequencyAnalysis.print(resultMap);
    }
}
