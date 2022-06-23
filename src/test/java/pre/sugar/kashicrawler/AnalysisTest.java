package pre.sugar.kashicrawler;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;
import pre.sugar.kashicrawler.business.WordFrequencyAnalysis;

import java.util.List;

/**
 * Author: Xiao Tang
 * Date: 2022/6/23 16:25
 * Description: \
 */
public class AnalysisTest {

    @Test
    public void test(){
        WordFrequencyAnalysis analysis = new WordFrequencyAnalysis(2,5,3);
        List<String> content = Lists.newArrayList("ABCDMFG","ABCDNFG","ABCDOFG");
        System.out.println(analysis.analysis(content));
    }
}
