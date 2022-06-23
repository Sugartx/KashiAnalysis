package pre.sugar.kashicrawler.business;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import pre.sugar.kashicrawler.entity.WordFrequency;

import java.util.List;
import java.util.Map;

/**
 * Author: Xiao Tang
 * Date: 2022/6/20 22:42
 * Description: \
 */
public class WordFrequencyAnalysis {

    private final int minLength;
    private final int maxLength;
    private final int threshold;

    public WordFrequencyAnalysis(int minLength, int maxLength, int threshold) {
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.threshold = threshold;
    }

    public Map<Integer, List<WordFrequency>> analysis(List<String> content){
        Map<Integer, Map<String, Integer>> frequencyMap = Maps.newHashMap();
        for (String row : content) {
            merge(analysis(row), frequencyMap);
        }
        Map<Integer, List<WordFrequency>> wordFrequencyListMap = Maps.newHashMap();
        for (Integer wordLength : frequencyMap.keySet()) {
            List<WordFrequency> wordFrequencyList = Lists.newArrayList();
            for (String word : frequencyMap.get(wordLength).keySet()) {
                int frequency = frequencyMap.get(wordLength).get(word);
                if (frequency < threshold){
                    continue;
                }
                wordFrequencyList.add(new WordFrequency(word, frequency));
                wordFrequencyListMap.put(wordLength, wordFrequencyList);
            }
            wordFrequencyList.sort((o1, o2) -> o2.getFrequency() - o1.getFrequency());
        }
        return wordFrequencyListMap;
    }

    /**
     * 单词频率分析器
     * @param content
     */
    public Map<Integer, Map<String, Integer>> analysis(String content){
        content = content.replace(" ","");
        content = content.replace("　","");
        content = content.replace("\n","");
        Map<Integer, Map<String, Integer>> frequencyMap = Maps.newHashMap();

        for (int i = minLength; i <= maxLength; i++) {
            Map<String, Integer> wordFrequencyMap = Maps.newHashMap();
            frequencyMap.put(i, wordFrequencyMap);
            for (int j = 0; j < content.length() - i; j++) {
                String word = content.substring(j, j + i);
                wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) + 1);
            }
        }

        return frequencyMap;
    }

    private void merge(Map<Integer, Map<String, Integer>> origin, Map<Integer, Map<String, Integer>> dest){
        for (int i = minLength; i <= maxLength; i++) {
            dest.putIfAbsent(i, Maps.newHashMap());
            dest.get(i).putAll(origin.get(i));
        }
    }

    public static void print(Map<Integer, List<WordFrequency>> map){
        map.forEach((wordLength, wordFrequencyList) -> {
            System.out.print(wordLength + "=");
            System.out.println(wordFrequencyList);
            System.out.println();
        });
    }
}
