import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        // インスタンス化
        Board board = new Board();
        Disc disc = new Disc(2, 2);
        Player player1 = new Player();
        Player player2 = new Player();
        Scanner scan = new Scanner(System.in);

        // 変数セット
        boolean retryFlg = false;

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
            // 他コマ方向格納リスト初期化
            board.initializeAllOtherDiscPos();

            // プレイヤーのターン切り替え
            boolean player1TurnFlg = player1.getTrun();
            boolean player2TurnFlg = player2.getTrun();

            if (!retryFlg) {
                if (!player1TurnFlg && !player2TurnFlg) {
                    player1.setTurn(true);
                } else if (player1TurnFlg && !player2TurnFlg) {
                    player1.setTurn(false);
                    player2.setTurn(true);
                } else if (!player1TurnFlg && player2TurnFlg) {
                    player1.setTurn(true);
                    player2.setTurn(false);
                }
            }
            player1TurnFlg = player1.getTrun();
            player2TurnFlg = player2.getTrun();

            String playerName = "";
            String discColor = "";
            if (player1TurnFlg) {
                playerName = player1.getPlayerName();
                discColor = player1.getDiscColor();
            } else {
                playerName = player2.getPlayerName();
                discColor = player2.getDiscColor();
            }

            System.out.println("=====" + playerName + "さん(" + discColor + ")のターン！" + "=====");

            // 反転対象行列番号リスト初期化
            ArrayList<ArrayList<ArrayList<Integer>>> allOtherDiscRowNoColumnNo = new ArrayList<>();

            // コマの配置場所の入力受付
            System.out.println("コマを配置したい行番号を入力してください。");
            String rowNo = scan.nextLine();
            int rowNo2int = Integer.parseInt(rowNo);
            System.out.println("コマを配置したい列番号を入力してください。");
            String columnNo = scan.nextLine();
            int columnNo2int = Integer.parseInt(columnNo);

            // 他色取得
            String otherDisc = board.getOtherDiscColor(discColor);

            // コマ配置可否チェック
            boolean isSetDiscFlg = board.isSetDisc(rowNo2int, columnNo2int);

            // 指定したマスにコマが置いてある場合は探索終了
            if (!isSetDiscFlg) {
                System.out.println("指定したマスには既にコマが配置されています。");

                retryFlg = true;

                continue;
            }

            // 他コマ隣接チェック
            boolean isNextToOtherDiscFlg = board.isNextToOtherDisc(rowNo2int, columnNo2int, otherDisc);
                    
            // 他コマが隣接していない場合は探索終了
            if (!isNextToOtherDiscFlg) {
                System.out.println("引っくり返せるコマがありません。");

                retryFlg = true;

                continue;
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
                boolean selfDiscFlg = false;
                boolean emptyFlg = false;
                boolean boardOutsideFlg = false;
                while (!board.getSelfDiscFlg()) {
                    // 他コマ行列番号リスト取得
                    otherDiscRowNoColumnNo = board.addOtherDiscRowNoColumnNo(rowNo2int, columnNo2int, targetDiscPos, otherDisc, discColor, noCounter);
                    
                    // 全他コマ行列番号リストに、他コマ行列番号リストを格納
                    otherDiscRowNoColumnNoAsPos.add(otherDiscRowNoColumnNo);

                    selfDiscFlg = board.getSelfDiscFlg();
                    emptyFlg = board.getEmptyFlg();
                    boardOutsideFlg = board.getBoardOutsideFlg();

                    // 自コマを検出したら探索終了
                    if (selfDiscFlg) {
                        break;
                    }

                    // 探索先にコマが配置されていないか、探索先が盤外だったら、全他コマ行列番号リストを初期化し探索終了
                    if (emptyFlg || boardOutsideFlg) {
                        ArrayList<ArrayList<Integer>> clearVal = new ArrayList<>();
                        otherDiscRowNoColumnNoAsPos = clearVal;
                        break;
                    }
                    noCounter++;
                }

                if(!selfDiscFlg) {
                    continue;
                }

                // 反転対象行列番号リストに、全他コマ行列番号リストを追加
                allOtherDiscRowNoColumnNo.add(otherDiscRowNoColumnNoAsPos);
            }

            if (allOtherDiscRowNoColumnNo.size() == 0) {
                System.out.println("探索結果、ひっくり返せるコマがありません。");

                retryFlg = true;
                
                continue;
            }
            
            for (ArrayList<ArrayList<Integer>> otherDiscRowNoColumnNo : allOtherDiscRowNoColumnNo) {
                for (ArrayList<Integer> rowNoColumnNo : otherDiscRowNoColumnNo) {
                    if (rowNoColumnNo.size() != 0) {
                        int otherDiscRowNo = rowNoColumnNo.get(0);
                        int otherDiscColumnNo = rowNoColumnNo.get(1);

                        // コマ配置(他コマをひっくり返す)
                        board.setDisc(rowNo2int, columnNo2int, discColor);
                        board.setDisc(otherDiscRowNo, otherDiscColumnNo, discColor);
                    }
                }
            }

            // コマ数算出
            String[][] boardList = board.getBoardList();
            disc.calcDiscNum(boardList);

            // オセロ盤面表示
            Integer blackNum = disc.getBlackNum();
            Integer whiteNum = disc.getWhiteNum();
            board.display(blackNum, whiteNum);

            // 勝敗判定
            boolean judgeGameFinishFlg = board.judgeGameFinish(blackNum, whiteNum);

            if (judgeGameFinishFlg) {
                if (blackNum > whiteNum) {
                    System.out.println("黒の勝利！");
                } else if (whiteNum > blackNum) {
                    System.out.println("白の勝利！");
                }
                break;
            }

            retryFlg = false;
        }
    }
}
