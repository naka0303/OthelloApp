import java.util.ArrayList;
import java.util.List;

public class Board {
    /**
     * オセロ盤面 8列 * 8行
     */
    private static String[][] boardList;

    /**
     * 他方コマ位置特定用enum
     */
    private static enum otherDiscPosEnum {
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
     * 他方コマ位置
     */
    private static int otherDiscPos = -1;

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
     * 指定色コマ方角特定
     * 
     * @param rowNo
     * @param columnNo
     * @param discColor
     */
    public ArrayList<Integer> seekDiscPos(int rowNo, int columnNo, String otherDisc) {
        int otherDiscRowNo = -1;
        int otherDiscColumnNo = -1;
        ArrayList<Integer> otherDiscRowNoColumnNo = new ArrayList<>();

        // 上に別色コマがあるかチェック
        if (boardList[rowNo-1][columnNo].equals(otherDisc)) {
            otherDiscPos = otherDiscPosEnum.TOP.ordinal();
            otherDiscRowNo = rowNo-1;
            otherDiscColumnNo = columnNo;
        }

        // 右上に別色コマがあるかチェック
        if (boardList[rowNo-1][columnNo+1].equals(otherDisc)) {
            otherDiscPos = otherDiscPosEnum.TOPRIGHT.ordinal();
            otherDiscRowNo = rowNo-1;
            otherDiscColumnNo = columnNo+1;
        }

        // 左上に別色コマがあるかチェック
        if (boardList[rowNo-1][columnNo-1].equals(otherDisc)) {
            otherDiscPos = otherDiscPosEnum.TOPLEFT.ordinal();
            otherDiscRowNo = rowNo-1;
            otherDiscColumnNo = columnNo-1;
        }

        // 下に別色コマがあるかチェック
        if (boardList[rowNo+1][columnNo].equals(otherDisc)) {
            otherDiscPos = otherDiscPosEnum.BOTTOM.ordinal();
            otherDiscRowNo = rowNo+1;
            otherDiscColumnNo = columnNo;
        }

        // 右下に別色コマがあるかチェック
        if (boardList[rowNo+1][columnNo+1].equals(otherDisc)) {
            otherDiscPos = otherDiscPosEnum.BOTTOMRIGHT.ordinal();
            otherDiscRowNo = rowNo+1;
            otherDiscColumnNo = columnNo+1;
        }

        // 左下に別色コマがあるかチェック
        if (boardList[rowNo+1][columnNo-1].equals(otherDisc)) {
            otherDiscPos = otherDiscPosEnum.BOTTOMLEFT.ordinal();
            otherDiscRowNo = rowNo+1;
            otherDiscColumnNo = columnNo-1;
        }

        // 右に別色コマがあるかチェック
        if (boardList[rowNo][columnNo+1].equals(otherDisc)) {
            otherDiscPos = otherDiscPosEnum.RIGHT.ordinal();
            otherDiscRowNo = rowNo;
            otherDiscColumnNo = columnNo+1;
        }

        // 左に別色コマがあるかチェック
        if (boardList[rowNo][columnNo-1].equals(otherDisc)) {
            otherDiscPos = otherDiscPosEnum.LEFT.ordinal();
            otherDiscRowNo = rowNo;
            otherDiscColumnNo = columnNo-1;
        }

        otherDiscRowNoColumnNo.add(otherDiscRowNo);
        otherDiscRowNoColumnNo.add(otherDiscColumnNo);

        return otherDiscRowNoColumnNo;
    }

    /** 別色取得
     * 
     * @param discColor
     */
    public String getOtherDiscColor(String discColor) {
        // 別色を特定
        if (discColor.equals("B")) {
            return "W";
        } else {
            return "B";
        }
    }
}
