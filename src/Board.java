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
     * 探索先コマ行番号/列番号/方向
     */
    private static int seekDiscRowNo = -1;
    private static int seekDiscColumnNo = -1;
    private static int seekDiscPos = -1;

    /**
     * 指定コマ方向格納リスト
     */
    private static ArrayList<Integer> allOtherDiscPos = new ArrayList<>();

    /**
     * 自コマ判定フラグ
     */
    private static boolean selfDiscFlg = false;

    /**
     * コマ配置判定フラグ
     */
    private static boolean emptyFlg = false;

    /**
     * 盤外判定フラグ
     * 
     */
    private static boolean boardOutsideFlg = false;

    /** 初期状態(1回のみ実行)
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

    /**
     * フラグ初期化
     */
    public void initializeFlg() {
        selfDiscFlg = false;
        emptyFlg = false;
        boardOutsideFlg = false;
    }

    /** コンソールに盤面表示
     * 
     */
    public void display() {
        System.out.println("1 2 3 4 5 6 7 8");
        int cnt = 1;
        for (String[] disc : boardList) {
            for (String d : disc) {
                if (d != null) {
                    System.out.print(d + " ");
                }
            }
            if (cnt - 1 >= 1) {
                System.out.print(cnt - 1);
            }
            System.out.println();
            cnt++;
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
    public boolean isNextToOtherDisc(int rowNo, int columnNo, String disc) {
        // 上に指定コマがあるかチェック
        if (boardList[rowNo-1][columnNo].equals(disc)) {
            allOtherDiscPos.add(discPosEnum.TOP.ordinal());
        }

        // 右上に指定コマがあるかチェック
        if (boardList[rowNo-1][columnNo+1].equals(disc)) {
            allOtherDiscPos.add(discPosEnum.TOPRIGHT.ordinal());
        }

        // 左上に指定コマがあるかチェック
        if (boardList[rowNo-1][columnNo-1].equals(disc)) {
            allOtherDiscPos.add(discPosEnum.TOPLEFT.ordinal());
        }

        // 下に指定コマがあるかチェック
        if (boardList[rowNo+1][columnNo].equals(disc)) {
            allOtherDiscPos.add(discPosEnum.BOTTOM.ordinal());
        }

        // 右下に指定コマがあるかチェック
        if (boardList[rowNo+1][columnNo+1].equals(disc)) {
            allOtherDiscPos.add(discPosEnum.BOTTOMRIGHT.ordinal());
        }

        // 左下に指定コマがあるかチェック
        if (boardList[rowNo+1][columnNo-1].equals(disc)) {
            allOtherDiscPos.add(discPosEnum.BOTTOMLEFT.ordinal());
        }

        // 右に指定コマがあるかチェック
        if (boardList[rowNo][columnNo+1].equals(disc)) {
            allOtherDiscPos.add(discPosEnum.RIGHT.ordinal());
        }

        // 左に指定コマがあるかチェック
        if (boardList[rowNo][columnNo-1].equals(disc)) {
            allOtherDiscPos.add(discPosEnum.LEFT.ordinal());
        }

        // 指定コマが隣接していなければそのままリターン
        if (allOtherDiscPos.size() == 0) {
            return false;
        }

        return true;
    }

    /**
     * 他コマ探索
     * 
     * @param seekDiscRowNo
     * @param seekDiscColumnNo
     * @param otherDisc
     * @param selfDisc
     */
    private void seekOtherDisc(int seekDiscRowNo, int seekDiscColumnNo, String otherDisc, String selfDisc) {
        if (boardList[seekDiscRowNo][seekDiscColumnNo].equals(otherDisc)) {
            selfDiscFlg = false;
            emptyFlg = false;
            boardOutsideFlg = false;
        } else if (boardList[seekDiscRowNo][seekDiscColumnNo].equals(selfDisc)) {
            selfDiscFlg = true;
            emptyFlg = false;
            boardOutsideFlg = false;
        } else if (boardList[seekDiscRowNo][seekDiscColumnNo].equals("-")) {
            selfDiscFlg = false;
            emptyFlg = true;
            boardOutsideFlg = false;
        } else if (seekDiscRowNo >= 1 && seekDiscRowNo <= 8
                   || seekDiscColumnNo >= 1 && seekDiscColumnNo <= 8) {
            selfDiscFlg = false;
            emptyFlg = false;
            boardOutsideFlg = true;
        }
    }

    /**
     * 他コマ行列番号リスト追加
     * 
     * @param rowNo
     * @param columnNo
     * @param otherDiscPos
     * @param otherDisc
     * @param selfDisc
     * @param noCounter
     */
    public ArrayList<Integer> addOtherDiscRowNoColumnNo(int rowNo, int columnNo, int otherDiscPos, String otherDisc, String selfDisc, int noCounter) {
        ArrayList<Integer> otherDiscRowNoColumnNo = new ArrayList<>();

        // 上に指定コマがある場合
        if (otherDiscPos == discPosEnum.TOP.ordinal()) {
            seekDiscRowNo = rowNo-noCounter;
            seekDiscColumnNo = columnNo;
        }

        // 右上に指定コマがある
        if (otherDiscPos == discPosEnum.TOPRIGHT.ordinal()) {
            seekDiscRowNo = rowNo-noCounter;
            seekDiscColumnNo = columnNo+noCounter;
        }

        // 左上に指定コマがある場合
        if (otherDiscPos == discPosEnum.TOPLEFT.ordinal()) {
            seekDiscRowNo = rowNo-noCounter;
            seekDiscColumnNo = columnNo-noCounter;
        }

        // 下に指定コマがある場合
        if (otherDiscPos == discPosEnum.BOTTOM.ordinal()) {
            seekDiscRowNo = rowNo+noCounter;
            seekDiscColumnNo = columnNo;
        }

        // 右下に指定コマがある場合
        if (otherDiscPos == discPosEnum.BOTTOMRIGHT.ordinal()) {
            seekDiscRowNo = rowNo+noCounter;
            seekDiscColumnNo = columnNo+noCounter;
        }

        // 左下に指定コマがある場合
        if (otherDiscPos == discPosEnum.BOTTOMLEFT.ordinal()) {
            seekDiscRowNo = rowNo+noCounter;
            seekDiscColumnNo = columnNo-noCounter;
        }

        // 右に指定コマがある場合
        if (otherDiscPos == discPosEnum.RIGHT.ordinal()) {
            seekDiscRowNo = rowNo;
            seekDiscColumnNo = columnNo+noCounter;
        }

        // 左に指定コマがある場合
        if (otherDiscPos == discPosEnum.LEFT.ordinal()) {
            seekDiscRowNo = rowNo;
            seekDiscColumnNo = columnNo-noCounter;
        }

        // 他コマ探索
        seekOtherDisc(seekDiscRowNo, seekDiscColumnNo, otherDisc, selfDisc);

        otherDiscRowNoColumnNo.add(seekDiscRowNo);
        otherDiscRowNoColumnNo.add(seekDiscColumnNo);
        
        return otherDiscRowNoColumnNo;
    }

    /*
     * 指定コマ方向取得
     */
    public int getTargetDiscPos() {
        return seekDiscPos;
    }

    /**
     * 指定コマ行番号/列番号/方向のリスト返却
     */
    public ArrayList<Integer> getTargetDiscPosRowNoColumnNo() {
        ArrayList<Integer> targetDiscPosRowNoColumnNo = new ArrayList<>();
        targetDiscPosRowNoColumnNo.add(seekDiscPos);
        targetDiscPosRowNoColumnNo.add(seekDiscRowNo);
        targetDiscPosRowNoColumnNo.add(seekDiscColumnNo);

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
    public ArrayList<Integer> getAllOtherDiscPos() {
        return allOtherDiscPos;
    }

    /**
     * 自コマ判定フラグ取得
     */
    public boolean getSelfDiscFlg() {
        return selfDiscFlg;
    }

    /**
     * コマ配置判定フラグ
     */
    public boolean getEmptyFlg() {
        return emptyFlg;
    }

    /** 
     * 盤外判定フラグ
     */
    public boolean getBoardOutsideFlg() {
        return boardOutsideFlg;
    }

    /**
     * 他コマ方向格納リスト初期化
     */
    public void initializeAllOtherDiscPos() {
        allOtherDiscPos = new ArrayList<>();
    }
}
