import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        boolean checkResult = false;

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
        player1.setDisc("W");
        player1.setTurn(false);
        player2.setPlayerName(player2Name);
        player2.setDisc("B");
        player2.setTurn(false);

        // オセロ盤面初期状態
        board.initialize();

        while (true) {
            // 他コマ方向格納リスト初期化
            board.initializeAllOtherDiscPos();

            // プレイヤーのターン切り替え
            boolean player1TurnFlg = player1.getTrun();
            boolean player2TurnFlg = player2.getTrun();
            if (!player1TurnFlg && !player2TurnFlg) {
                player1.setTurn(true);
            } else if (player1TurnFlg && !player2TurnFlg) {
                player1.setTurn(false);
                player2.setTurn(true);
            } else if (!player1TurnFlg && player2TurnFlg) {
                player1.setTurn(true);
                player2.setTurn(false);
            }
            player1TurnFlg = player1.getTrun();
            player2TurnFlg = player2.getTrun();

            String playerName = "";
            String disc = "";
            if (player1TurnFlg) {
                playerName = player1.getPlayerName();
                disc = player1.getDisc();
            } else {
                playerName = player2.getPlayerName();
                disc = player2.getDisc();
            }

            System.out.println("=====" + playerName + "さんのターン！" + "=====");

            // 反転対象行列番号リスト初期化
            ArrayList<ArrayList<ArrayList<Integer>>> allOtherDiscRowNoColumnNo = new ArrayList<>();

            // コマの配置場所の入力受付
            System.out.println("コマを配置したい行番号を入力してください。");
            String rowNo = scan.nextLine();
            int rowNo2int = Integer.parseInt(rowNo);
            System.out.println("コマを配置したい列番号を入力してください。");
            String columnNo = scan.nextLine();
            int columnNo2int = Integer.parseInt(columnNo);

            // 自コマ配置
            board.setDisc(rowNo2int, columnNo2int, disc);

            // 他色取得
            String otherDisc = board.getOtherDisc(disc);

            // 他コマ隣接チェック
            boolean isNextToOtherDiscFlg = board.isNextToOtherDisc(rowNo2int, columnNo2int, otherDisc);
                    
            // 他コマが隣接していない場合は探索終了
            if (!isNextToOtherDiscFlg) {
                System.out.println("引っくり返せるコマがありません。");

                // コマ配置取消
                // board.setDisc(rowNo2int, columnNo2int, "-");

                break;
            }

            // 他コマ方向格納リスト取得
            ArrayList<Integer> allTargetDiscPos = new ArrayList<>();
            allTargetDiscPos = board.getAllOtherDiscPos();

            // 他コマを検出した数分ループ
            for (int targetDiscPos : allTargetDiscPos) {
                // フラグ初期化
                board.initializeFlg();

                // 他コマ行列番号リスト
                ArrayList<Integer> otherDiscRowNoColumnNo = new ArrayList<>();
                // 全他コマ行列番号リスト
                ArrayList<ArrayList<Integer>> otherDiscRowNoColumnNoAsPos = new ArrayList<>();
                
                int noCounter = 1;
                while (!board.getSelfDiscFlg()) {
                    // 他コマ行列番号リスト取得
                    otherDiscRowNoColumnNo = board.addOtherDiscRowNoColumnNo(rowNo2int, columnNo2int, targetDiscPos, otherDisc, disc, noCounter);
                    
                    // 全他コマ行列番号リストに、他コマ行列番号リストを格納kakunou
                    otherDiscRowNoColumnNoAsPos.add(otherDiscRowNoColumnNo);

                    boolean selfDiscFlg = board.getSelfDiscFlg();
                    boolean emptyFlg = board.getEmptyFlg();
                    boolean boardOutsideFlg = board.getBoardOutsideFlg();

                    // 自コマを検出したら処理終了
                    if (selfDiscFlg) {
                        break;
                    }

                    // 探索先にコマが配置されていないか、探索先が盤外だったら、全他コマ行列番号リストを初期化し処理終了
                    if (emptyFlg|| boardOutsideFlg) {
                        ArrayList<ArrayList<Integer>> clearVal = new ArrayList<>();
                        otherDiscRowNoColumnNoAsPos = clearVal;
                        break;
                    }

                    noCounter++;
                }

                // 反転対象行列番号リストに、全他コマ行列番号リストを追加
                allOtherDiscRowNoColumnNo.add(otherDiscRowNoColumnNoAsPos);
            }

            for (ArrayList<ArrayList<Integer>> otherDiscRowNoColumnNo : allOtherDiscRowNoColumnNo) {
                for (ArrayList<Integer> rowNoColumnNo : otherDiscRowNoColumnNo) {
                    if (rowNoColumnNo.size() != 0) {
                        int otherDiscRowNo = rowNoColumnNo.get(0);
                        int otherDiscColumnNo = rowNoColumnNo.get(1);

                        // コマ配置(他コマをひっくり返す)
                        board.setDisc(otherDiscRowNo, otherDiscColumnNo, disc);
                    }
                }
            }

            // オセロ盤面表示
            board.display();
        }
    }
}
