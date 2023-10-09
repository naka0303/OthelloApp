public class Disc {
    private Integer blackNum;
    private Integer whiteNum;

    public Disc(Integer blackNum, Integer whiteNum) {
        this.blackNum = blackNum;
        this.whiteNum = whiteNum;
    }

    // getter
    public Integer getBlackNum() {
        return this.blackNum;
    }

    public Integer getWhiteNum() {
        return this.whiteNum;
    }

    /**
     * コマ数算出
     */
    public void calcDiscNum(String[][] boardList) {
        int blackCnt = 0;
        int whiteCnt = 0;
        for (String[] rowNoColumnNo : boardList) {
            for (String var : rowNoColumnNo) {
                if (var == "B") {
                    blackCnt++;
                } else if (var == "W") {
                    whiteCnt++;
                }
            }
        }
        this.blackNum = blackCnt;
        this.whiteNum = whiteCnt;
    }
}
