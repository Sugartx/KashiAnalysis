package pre.sugar.kashicrawler.entity;

/**
 * Author: Xiao Tang
 * Date: 2022/6/23 10:08
 * Description: \
 */
public enum SelectTypes {

    ARTIST(1,"歌手"),
    SONG(2,"曲名"),
    LYRIC(3,"词作"),
    ARTIST_HIRAGANA(5, "歌手平假名"),
    SONG_HIRAGANA(6, "歌曲平假名"),
    COMPOSER(8,"曲作"),
    ;

    private int val;
    private String cnDescription;

    SelectTypes(int val, String cnDescription) {
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
