package pre.sugar.kashicrawler.entity;

/**
 * Author: Xiao Tang
 * Date: 2022/6/23 10:21
 * Description: \
 */
public enum FuzzyTypes {

    START_WITH(1,"开始于"),
    END_WITH(2, "结束于"),
    CONTAIN(3, "包含"),
    SAME(4,"完全一致"),
    ;

    private int val;
    private String cnDescription;

    FuzzyTypes(int val, String cnDescription) {
        this.val = val;
        this.cnDescription = cnDescription;
    }

    public int getVal() {
        return val;
    }

    public String getCnDescription() {
        return cnDescription;
    }
}
