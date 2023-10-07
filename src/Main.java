import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    static String discColor;
    static String rowNo;
    static String columnNo;
    static int rowNo2int;
    static int columnNo2int;
    static String otherDisc;
    static boolean checkResult = false;
    static ArrayList<Integer> otherDiscPosRowNoColumnNo;

    public static void main(String[] args) throws Exception {
        // インスタンス化
        Board board = new Board();
        Player player1 = new Player();
        Player player2 = new Player();
        Scanner scan = new Scanner(System.in);

        // プレイヤー名/コマ色設定
        System.out.println("プレイヤー1の名前を入力してください");
        String player1Name = scan.nextLine();
        System.out.println("プレイヤー2の名前を入力してください");
        String player2Name = scan.nextLine();
        player1.setPlayerName(player1Name);
        player1.setDiscColor("W");
        player1.setTurn(false);
        player2.setPlayerName(player2Name);
        player2.setDiscColor("B");
        player2.setTurn(false);

        // オセロ盤面初期状態
        board.initialize();

        while (true) {
            // プレイヤー1のターン中は以下ループ
            player1.setTurn(true);
            while (player1.getTrun()) {
                board.initializeAllTargetDiscPos();

                System.out.println("=====" + player1.getPlayerName() + "さんのターン！" + "=====");

                ArrayList<ArrayList<ArrayList<Integer>>> allOtherDiscRowNoColumnNo = new ArrayList<>();

                // コマの配置場所の入力受付
                System.out.println("コマを配置したい行番号を入力してください。");
                rowNo = scan.nextLine();
                rowNo2int = Integer.parseInt(rowNo);
                System.out.println("コマを配置したい列番号を入力してください。");
                columnNo = scan.nextLine();
                columnNo2int = Integer.parseInt(columnNo);

                // コマ配置
                board.setDisc(rowNo2int, columnNo2int, player1.getDiscColor());

                // 別コマ色取得
                otherDisc = board.getOtherDisc(player1.getDiscColor());

                // 指定コマ隣接チェック(別コマ指定)
                boolean isNextToOtherDisc = board.isNextToTargetDisc(rowNo2int, columnNo2int, otherDisc);
                    
                // 別コマが隣接していない場合は探索終了
                if (!isNextToOtherDisc) {
                    System.out.println("引っくり返せるコマがありません。");

                    // コマ配置取消
                    // board.setDisc(rowNo2int, columnNo2int, "-");

                    break;
                }

                // 指定コマ方向格納リスト取得
                ArrayList<Integer> allTargetDiscPos = board.getAllTargetDiscPos();

                for (int targetDiscPos : allTargetDiscPos) {
                    board.initializeFlg();
                    System.out.println(targetDiscPos);
                    ArrayList<Integer> targetDiscRowNoColumnNo = new ArrayList<>();
                    ArrayList<ArrayList<Integer>> targetDiscRowNoColumnNoAsPos = new ArrayList<>();
                    int noCounter = 1;
                    while (!board.getSelfDiscFlg()) {
                        // 指定コマ探索
                        targetDiscRowNoColumnNo = board.addOtherDiscRowNoColumnNo(rowNo2int, columnNo2int, targetDiscPos, otherDisc, player1.getDiscColor(), noCounter);
                    
                        targetDiscRowNoColumnNoAsPos.add(targetDiscRowNoColumnNo);

                        if (board.getSelfDiscFlg()) {
                            break;
                        }

                        if (board.getSetDiscFlg() || board.getBoardOutsideFlg()) {
                            ArrayList<ArrayList<Integer>> clearVal = new ArrayList<>();
                            targetDiscRowNoColumnNoAsPos = clearVal;
                            break;
                        }

                        noCounter++;
                    }

                    // 全ての別コマ行番号/列番号/方向のリスト取得
                    allOtherDiscRowNoColumnNo.add(targetDiscRowNoColumnNoAsPos);
                }

                for (ArrayList<ArrayList<Integer>> otherDiscRowNoColumnNo : allOtherDiscRowNoColumnNo) {
                    for (ArrayList<Integer> rowNoColumnNo : otherDiscRowNoColumnNo) {
                        if (rowNoColumnNo.size() != 0) {
                            int otherDiscRowNo = rowNoColumnNo.get(0);
                            int otherDiscColumnNo = rowNoColumnNo.get(1);

                            // コマ配置(別コマをひっくり返す)
                            board.setDisc(otherDiscRowNo, otherDiscColumnNo, player1.getDiscColor());
                        }
                    }
                }

                // オセロ盤面表示
                board.display();

                // ターン終了
                player1.setTurn(false);
            }

            // プレイヤー2のターン中は以下ループ
            player2.setTurn(true);
            while (player2.getTrun()) {
                board.initializeAllTargetDiscPos();
                
                System.out.println("=====" + player2.getPlayerName() + "さんのターン！" + "=====");

                ArrayList<ArrayList<ArrayList<Integer>>> allOtherDiscRowNoColumnNo = new ArrayList<>();

                // コマの配置場所の入力受付
                System.out.println("コマを配置したい行番号を入力してください。");
                rowNo = scan.nextLine();
                rowNo2int = Integer.parseInt(rowNo);
                System.out.println("コマを配置したい列番号を入力してください。");
                columnNo = scan.nextLine();
                columnNo2int = Integer.parseInt(columnNo);

                // コマ配置
                board.setDisc(rowNo2int, columnNo2int, player2.getDiscColor());

                // 別コマ色取得
                otherDisc = board.getOtherDisc(player2.getDiscColor());

                // 指定コマ隣接チェック(別コマ指定)
                boolean isNextToOtherDisc = board.isNextToTargetDisc(rowNo2int, columnNo2int, otherDisc);
                    
                // 別コマが隣接していない場合は探索終了
                if (!isNextToOtherDisc) {
                    System.out.println("引っくり返せるコマがありません。");

                    // コマ配置取消
                    // board.setDisc(rowNo2int, columnNo2int, "-");

                    break;
                }

                // 指定コマ方向格納リスト取得
                ArrayList<Integer> allTargetDiscPos = board.getAllTargetDiscPos();

                for (int targetDiscPos : allTargetDiscPos) {
                    board.initializeFlg();
                    ArrayList<Integer> targetDiscRowNoColumnNo = new ArrayList<>();
                    ArrayList<ArrayList<Integer>> targetDiscRowNoColumnNoAsPos = new ArrayList<>();
                    int noCounter = 1;
                    while (!board.getSelfDiscFlg()) {
                        // 指定コマ探索
                        targetDiscRowNoColumnNo = board.addOtherDiscRowNoColumnNo(rowNo2int, columnNo2int, targetDiscPos, otherDisc, player2.getDiscColor(), noCounter);
                    
                        targetDiscRowNoColumnNoAsPos.add(targetDiscRowNoColumnNo);

                        if (board.getSelfDiscFlg()) {
                            break;
                        }

                        if (board.getSetDiscFlg() || board.getBoardOutsideFlg()) {
                            ArrayList<ArrayList<Integer>> clearVal = new ArrayList<>();
                            targetDiscRowNoColumnNoAsPos = clearVal;
                            break;
                        }

                        noCounter++;
                    }

                    // 全ての別コマ行番号/列番号/方向のリスト取得
                    allOtherDiscRowNoColumnNo.add(targetDiscRowNoColumnNoAsPos);
                }

                for (ArrayList<ArrayList<Integer>> otherDiscRowNoColumnNo : allOtherDiscRowNoColumnNo) {
                    for (ArrayList<Integer> rowNoColumnNo : otherDiscRowNoColumnNo) {
                        if (rowNoColumnNo.size() != 0) {
                            int otherDiscRowNo = rowNoColumnNo.get(0);
                            int otherDiscColumnNo = rowNoColumnNo.get(1);

                            // コマ配置(別コマをひっくり返す)
                            board.setDisc(otherDiscRowNo, otherDiscColumnNo, player2.getDiscColor());
                        }
                    }
                }

                // オセロ盤面表示
                board.display();

                // ターン終了
                player2.setTurn(false);
            }
        }
    }
}
