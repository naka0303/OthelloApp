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
    private static ArrayList<Integer> allTargetDiscPos = new ArrayList<>();

    /**
     * 自コマ判定フラグ
     */
    private static boolean selfDiscFlg = false;

    /**
     * コマ配置判定フラグ
     */
    private static boolean setDiscFlg = false;

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
     * ターン切り替え初期化
     */
    public void initializeFlg() {
        selfDiscFlg = false;
        setDiscFlg = false;
        boardOutsideFlg = false;
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
    public ArrayList<Integer> seekTargetDisc(int rowNo, int columnNo, int otherDiscPos, String otherDisc, String selfDisc, int noCounter) {
        ArrayList<Integer> seekDiscRowNoColumnNo = new ArrayList<>();

        // 上に指定コマがある場合
        if (otherDiscPos == discPosEnum.TOP.ordinal()) {
            seekDiscRowNo = rowNo-noCounter;
            seekDiscColumnNo = columnNo;
            if (boardList[seekDiscRowNo][seekDiscColumnNo].equals(otherDisc)) {
                selfDiscFlg = false;
                setDiscFlg = false;
                boardOutsideFlg = false;
            } else if (boardList[seekDiscRowNo][seekDiscColumnNo].equals(selfDisc)) {
                selfDiscFlg = true;
                setDiscFlg = false;
                boardOutsideFlg = false;
                return seekDiscRowNoColumnNo;
            } else if (boardList[seekDiscRowNo][seekDiscColumnNo].equals("-")) {
                selfDiscFlg = false;
                setDiscFlg = true;
                boardOutsideFlg = false;
                return seekDiscRowNoColumnNo;
            } else if (seekDiscRowNo >= 1 && seekDiscRowNo <= 8
                       || seekDiscColumnNo >= 1 && seekDiscColumnNo <= 8) {
                selfDiscFlg = false;
                setDiscFlg = false;
                boardOutsideFlg = true;
                return seekDiscRowNoColumnNo;
            }
        }

        // 右上に指定コマがある
        if (otherDiscPos == discPosEnum.TOPRIGHT.ordinal()) {
            seekDiscRowNo = rowNo-noCounter;
            seekDiscColumnNo = columnNo+noCounter;
            if (boardList[seekDiscRowNo][seekDiscColumnNo].equals(otherDisc)) {
                selfDiscFlg = false;
                setDiscFlg = false;
                boardOutsideFlg = false;
            } else if (boardList[seekDiscRowNo][seekDiscColumnNo].equals(selfDisc)) {
                selfDiscFlg = true;
                setDiscFlg = false;
                boardOutsideFlg = false;
                return seekDiscRowNoColumnNo;
            } else if (boardList[seekDiscRowNo][seekDiscColumnNo].equals("-")) {
                selfDiscFlg = false;
                setDiscFlg = true;
                boardOutsideFlg = false;
                return seekDiscRowNoColumnNo;
            } else if (seekDiscRowNo >= 1 && seekDiscRowNo <= 8
                       || seekDiscColumnNo >= 1 && seekDiscColumnNo <= 8) {
                selfDiscFlg = false;
                setDiscFlg = false;
                boardOutsideFlg = true;
                return seekDiscRowNoColumnNo;
            }
        }

        // 左上に指定コマがある場合
        if (otherDiscPos == discPosEnum.TOPLEFT.ordinal()) {
            seekDiscRowNo = rowNo-noCounter;
            seekDiscColumnNo = columnNo-noCounter;
            if (boardList[seekDiscRowNo][seekDiscColumnNo].equals(otherDisc)) {
                selfDiscFlg = false;
                setDiscFlg = false;
                boardOutsideFlg = false;
            } else if (boardList[seekDiscRowNo][seekDiscColumnNo].equals(selfDisc)) {
                selfDiscFlg = true;
                setDiscFlg = false;
                boardOutsideFlg = false;
                return seekDiscRowNoColumnNo;
            } else if (boardList[seekDiscRowNo][seekDiscColumnNo].equals("-")) {
                selfDiscFlg = false;
                setDiscFlg = true;
                boardOutsideFlg = false;
                return seekDiscRowNoColumnNo;
            } else if (seekDiscRowNo >= 1 && seekDiscRowNo <= 8
                       || seekDiscColumnNo >= 1 && seekDiscColumnNo <= 8) {
                selfDiscFlg = false;
                setDiscFlg = false;
                boardOutsideFlg = true;
                return seekDiscRowNoColumnNo;
            }
        }

        // 下に指定コマがある場合
        if (otherDiscPos == discPosEnum.BOTTOM.ordinal()) {
            seekDiscRowNo = rowNo+noCounter;
            seekDiscColumnNo = columnNo;
            if (boardList[seekDiscRowNo][seekDiscColumnNo].equals(otherDisc)) {
                selfDiscFlg = false;
                setDiscFlg = false;
                boardOutsideFlg = false;
            } else if (boardList[seekDiscRowNo][seekDiscColumnNo].equals(selfDisc)) {
                selfDiscFlg = true;
                setDiscFlg = false;
                boardOutsideFlg = false;
                return seekDiscRowNoColumnNo;
            } else if (boardList[seekDiscRowNo][seekDiscColumnNo].equals("-")) {
                selfDiscFlg = false;
                setDiscFlg = true;
                boardOutsideFlg = false;
                return seekDiscRowNoColumnNo;
            } else if (seekDiscRowNo >= 1 && seekDiscRowNo <= 8
                       || seekDiscColumnNo >= 1 && seekDiscColumnNo <= 8) {
                selfDiscFlg = false;
                setDiscFlg = false;
                boardOutsideFlg = true;
                return seekDiscRowNoColumnNo;
            }
        }

        // 右下に指定コマがある場合
        if (otherDiscPos == discPosEnum.BOTTOMRIGHT.ordinal()) {
            seekDiscRowNo = rowNo+noCounter;
            seekDiscColumnNo = columnNo+noCounter;
            if (boardList[seekDiscRowNo][seekDiscColumnNo].equals(otherDisc)) {
                selfDiscFlg = false;
                setDiscFlg = false;
                boardOutsideFlg = false;
            } else if (boardList[seekDiscRowNo][seekDiscColumnNo].equals(selfDisc)) {
                selfDiscFlg = true;
                setDiscFlg = false;
                boardOutsideFlg = false;
                return seekDiscRowNoColumnNo;
            } else if (boardList[seekDiscRowNo][seekDiscColumnNo].equals("-")) {
                selfDiscFlg = false;
                setDiscFlg = true;
                boardOutsideFlg = false;
                return seekDiscRowNoColumnNo;
            } else if (seekDiscRowNo >= 1 && seekDiscRowNo <= 8
                       || seekDiscColumnNo >= 1 && seekDiscColumnNo <= 8) {
                selfDiscFlg = false;
                setDiscFlg = false;
                boardOutsideFlg = true;
                return seekDiscRowNoColumnNo;
            }
        }

        // 左下に指定コマがある場合
        if (otherDiscPos == discPosEnum.BOTTOMLEFT.ordinal()) {
            seekDiscRowNo = rowNo+noCounter;
            seekDiscColumnNo = columnNo-noCounter;
            if (boardList[seekDiscRowNo][seekDiscColumnNo].equals(otherDisc)) {
                selfDiscFlg = false;
                setDiscFlg = false;
                boardOutsideFlg = false;
            } else if (boardList[seekDiscRowNo][seekDiscColumnNo].equals(selfDisc)) {
                selfDiscFlg = true;
                setDiscFlg = false;
                boardOutsideFlg = false;
                return seekDiscRowNoColumnNo;
            } else if (boardList[seekDiscRowNo][seekDiscColumnNo].equals("-")) {
                selfDiscFlg = false;
                setDiscFlg = true;
                boardOutsideFlg = false;
                return seekDiscRowNoColumnNo;
            } else if (seekDiscRowNo >= 1 && seekDiscRowNo <= 8
                       || seekDiscColumnNo >= 1 && seekDiscColumnNo <= 8) {
                selfDiscFlg = false;
                setDiscFlg = false;
                boardOutsideFlg = true;
                return seekDiscRowNoColumnNo;
            }
        }

        // 右に指定コマがある場合
        if (otherDiscPos == discPosEnum.RIGHT.ordinal()) {
            seekDiscRowNo = rowNo;
            seekDiscColumnNo = columnNo+noCounter;
            if (boardList[seekDiscRowNo][seekDiscColumnNo].equals(otherDisc)) {
                selfDiscFlg = false;
                setDiscFlg = false;
                boardOutsideFlg = false;
            } else if (boardList[seekDiscRowNo][seekDiscColumnNo].equals(selfDisc)) {
                selfDiscFlg = true;
                setDiscFlg = false;
                boardOutsideFlg = false;
                return seekDiscRowNoColumnNo;
            } else if (boardList[seekDiscRowNo][seekDiscColumnNo].equals("-")) {
                selfDiscFlg = false;
                setDiscFlg = true;
                boardOutsideFlg = false;
                return seekDiscRowNoColumnNo;
            } else if (seekDiscRowNo >= 1 && seekDiscRowNo <= 8
                       || seekDiscColumnNo >= 1 && seekDiscColumnNo <= 8) {
                selfDiscFlg = false;
                setDiscFlg = false;
                boardOutsideFlg = true;
                return seekDiscRowNoColumnNo;
            }
        }

        // 左に指定コマがある場合
        if (otherDiscPos == discPosEnum.LEFT.ordinal()) {
            seekDiscRowNo = rowNo;
            seekDiscColumnNo = columnNo-noCounter;
            if (boardList[seekDiscRowNo][seekDiscColumnNo].equals(otherDisc)) {
                selfDiscFlg = false;
                setDiscFlg = false;
                boardOutsideFlg = false;
            } else if (boardList[seekDiscRowNo][seekDiscColumnNo].equals(selfDisc)) {
                selfDiscFlg = true;
                setDiscFlg = false;
                boardOutsideFlg = false;
                return seekDiscRowNoColumnNo;
            } else if (boardList[seekDiscRowNo][seekDiscColumnNo].equals("-")) {
                selfDiscFlg = false;
                setDiscFlg = true;
                boardOutsideFlg = false;
                return seekDiscRowNoColumnNo;
            } else if (seekDiscRowNo >= 1 && seekDiscRowNo <= 8
                       || seekDiscColumnNo >= 1 && seekDiscColumnNo <= 8) {
                selfDiscFlg = false;
                setDiscFlg = false;
                boardOutsideFlg = true;
                return seekDiscRowNoColumnNo;
            }
        }

        seekDiscRowNoColumnNo.add(seekDiscRowNo);
        seekDiscRowNoColumnNo.add(seekDiscColumnNo);
        
        return seekDiscRowNoColumnNo;
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
    public ArrayList<Integer> getAllTargetDiscPos() {
        return allTargetDiscPos;
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
    public boolean getSetDiscFlg() {
        return setDiscFlg;
    }

    /** 
     * 盤外判定フラグ
     */
    public boolean getBoardOutsideFlg() {
        return boardOutsideFlg;
    }

    /**
     * 指定コマ方向格納リスト初期化
     */
    public void initializeAllTargetDiscPos() {
        allTargetDiscPos = new ArrayList<>();
    }
}
