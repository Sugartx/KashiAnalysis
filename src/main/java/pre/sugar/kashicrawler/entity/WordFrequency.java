package pre.sugar.kashicrawler.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Author: Xiao Tang
 * Date: 2022/6/20 23:06
 * Description: \
 */

@Data
@AllArgsConstructor
public class WordFrequency {
    private String word;
    private int frequency;

    @Override
    public String toString() {
        return word + "=" + frequency;
    }
}
