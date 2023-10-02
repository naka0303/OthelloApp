import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    /**
     * オセロ盤面 8列 * 8行
     */
    private static String[][] boardList;

    /**
     * 指定コマ位置特定用enum
     */
    private static enum discPosEnum {
        TOP,
        TOPRIGHT,
        TOPLEFT,
        BOTTOM,
        BOTTOMRIGHT,
        BOTTOMLEFT,
        RIGHT,
        LEFT
    };

    /**
     * 指定コマ行番号/列番号/方向
     */
    private static int discRowNo = -1;
    private static int discColumnNo = -1;
    private static int discPos = -1;

    /**
     * 指定コマ方向格納リスト
     */
    private static ArrayList<Integer> allTargetDiscPos = new ArrayList<>();

    /** 初期状態
     * 
     */
    public void initialize() {
        // 盤面行列リスト
        boardList = new String[9][9];

        // 盤面の全ての行列に"-"を値として格納
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                boardList[i][j] = "-";
            }
        }

        // コマ配置
        setDisc(4, 4, "B");
        setDisc(4, 5, "W");
        setDisc(5, 4, "W");
        setDisc(5, 5, "B");

        // コンソールに盤面表示
        display();
    }

    /** コンソールに盤面表示
     * 
     */
    public void display() {
        for (String[] disc : boardList) {
            for (String d : disc) {
                if (d != null) {
                    System.out.print(d + " ");
                }
            }
            System.out.println();
        }
    }

    /** コマ配置
     * 
     * @param rowNo
     * @param columnNo
     * @param discColor
     */
    public void setDisc(int rowNo, int columnNo, String discColor) {
        boardList[rowNo][columnNo] = discColor;
    }

    /**
     * 指定コマ隣接チェック
     * 
     * @param rowNo
     * @param columnNo
     * @param otherDisc
     */
    public boolean isNextToTargetDisc(int rowNo, int columnNo, String disc) {
        // 上に指定コマがあるかチェック
        if (boardList[rowNo-1][columnNo].equals(disc)) {
            allTargetDiscPos.add(discPosEnum.TOP.ordinal());
        }

        // 右上に指定コマがあるかチェック
        if (boardList[rowNo-1][columnNo+1].equals(disc)) {
            allTargetDiscPos.add(discPosEnum.TOPRIGHT.ordinal());
        }

        // 左上に指定コマがあるかチェック
        if (boardList[rowNo-1][columnNo-1].equals(disc)) {
            allTargetDiscPos.add(discPosEnum.TOPLEFT.ordinal());
        }

        // 下に指定コマがあるかチェック
        if (boardList[rowNo+1][columnNo].equals(disc)) {
            allTargetDiscPos.add(discPosEnum.BOTTOM.ordinal());
        }

        // 右下に指定コマがあるかチェック
        if (boardList[rowNo+1][columnNo+1].equals(disc)) {
            allTargetDiscPos.add(discPosEnum.BOTTOMRIGHT.ordinal());
        }

        // 左下に指定コマがあるかチェック
        if (boardList[rowNo+1][columnNo-1].equals(disc)) {
            allTargetDiscPos.add(discPosEnum.BOTTOMLEFT.ordinal());
        }

        // 右に指定コマがあるかチェック
        if (boardList[rowNo][columnNo+1].equals(disc)) {
            allTargetDiscPos.add(discPosEnum.RIGHT.ordinal());
        }

        // 左に指定コマがあるかチェック
        if (boardList[rowNo][columnNo-1].equals(disc)) {
            allTargetDiscPos.add(discPosEnum.LEFT.ordinal());
        }

        // 指定コマが隣接していなければそのままリターン
        if (allTargetDiscPos.size() == 0) {
            return false;
        }

        return true;
    }

    /**
     * 指定コマ探索
     * 
     * @param rowNo
     * @param columnNo
     * @param discPos
     */
    public ArrayList<Integer> seekTargetDisc(int rowNo, int columnNo, int discPos, String disc, int noCounter) {
        // 上に指定コマがある場合
        if (discPos == discPosEnum.TOP.ordinal()) {
            if (boardList[rowNo-noCounter][columnNo].equals(disc)) {
                discRowNo = rowNo-noCounter;
                discColumnNo = columnNo;
            }
        }

        // 右上に指定コマがある
        if (discPos == discPosEnum.TOPRIGHT.ordinal()) {
            if (boardList[rowNo-noCounter][columnNo+noCounter].equals(disc)) {
                discRowNo = rowNo-noCounter;
                discColumnNo = columnNo+noCounter;
            }
        }

        // 左上に指定コマがある場合
        if (discPos == discPosEnum.TOPLEFT.ordinal()) {
            if (boardList[rowNo-noCounter][columnNo-noCounter].equals(disc)) {
                discRowNo = rowNo-noCounter;
                discColumnNo = columnNo-noCounter;
            }
        }

        // 下に指定コマがある場合
        if (discPos == discPosEnum.BOTTOM.ordinal()) {
            if (boardList[rowNo+noCounter][columnNo].equals(disc)) {
                discRowNo = rowNo+noCounter;
                discColumnNo = columnNo;
            }
        }

        // 右下に指定コマがある場合
        if (discPos == discPosEnum.BOTTOMRIGHT.ordinal()) {
            if (boardList[rowNo+noCounter][columnNo+1].equals(disc)) {
                discRowNo = rowNo+noCounter;
                discColumnNo = columnNo+noCounter;
            }
        }

        // 左下に指定コマがある場合
        if (discPos == discPosEnum.BOTTOMLEFT.ordinal()) {
            if (boardList[rowNo+noCounter][columnNo-noCounter].equals(disc)) {
                discRowNo = rowNo+noCounter;
                discColumnNo = columnNo-noCounter;
            }
        }

        // 右に指定コマがある場合
        if (discPos == discPosEnum.RIGHT.ordinal()) {
            if (boardList[rowNo][columnNo+noCounter].equals(disc)) {
                discRowNo = rowNo;
                discColumnNo = columnNo+noCounter;
            }
        }

        // 左に指定コマがある場合
        if (discPos == discPosEnum.LEFT.ordinal()) {
            if (boardList[rowNo][columnNo-noCounter].equals(disc)) {
                discRowNo = rowNo;
                discColumnNo = columnNo-noCounter;
            }
        }

        ArrayList<Integer> targetDiscRowNoColumnNo = new ArrayList<>();
        targetDiscRowNoColumnNo.add(discRowNo);
        targetDiscRowNoColumnNo.add(discColumnNo);

        return targetDiscRowNoColumnNo;
    }

    /*
     * 指定コマ方向取得
     */
    public int getTargetDiscPos() {
        return discPos;
    }

    /**
     * 指定コマ行番号/列番号/方向のリスト返却
     */
    public ArrayList<Integer> getTargetDiscPosRowNoColumnNo() {
        ArrayList<Integer> targetDiscPosRowNoColumnNo = new ArrayList<>();
        targetDiscPosRowNoColumnNo.add(discPos);
        targetDiscPosRowNoColumnNo.add(discRowNo);
        targetDiscPosRowNoColumnNo.add(discColumnNo);

        return targetDiscPosRowNoColumnNo;
    }

    /** 指定取得
     * 
     * @param discColor
     */
    public String getOtherDisc(String discColor) {
        // 指定を特定
        if (discColor.equals("B")) {
            return "W";
        } else {
            return "B";
        }
    }

    /**
     * 指定コマ方向格納リスト取得
     * @return
     */
    public ArrayList<Integer> getAllTargetDiscPos() {
        return allTargetDiscPos;
    }
}
