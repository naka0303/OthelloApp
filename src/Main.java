import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static String discColor;
    static String rowNo;
    static String columnNo;
    static int rowNo2int;
    static int columnNo2int;
    static String otherDiscColor;
    static ArrayList<Integer> otherDiscRowNoColumnNo;
    static boolean checkResult = false;
    static int otherDiscRowNo = -1;
    static int otherDiscColumnNo = -1;

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
                System.out.println("=====" + player1.getPlayerName() + "さんのターン！" + "=====");

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
                otherDiscColor = board.getOtherDiscColor(player1.getDiscColor());

                // 指定色コマ方角特定
                otherDiscRowNoColumnNo = board.seekDiscPos(rowNo2int, columnNo2int, otherDiscColor);
                otherDiscRowNo = otherDiscRowNoColumnNo.get(0);
                otherDiscColumnNo = otherDiscRowNoColumnNo.get(1);

                // 別コマ隣接チェック
                if (otherDiscRowNo == -1 && otherDiscColumnNo == -1) {
                    System.out.println("引っくり返せるコマがありません。");

                    // コマ配置取消
                    board.setDisc(rowNo2int, columnNo2int, "-");

                    continue;
                }

                // コマ配置(隣接している別コマをひっくり返す)
                board.setDisc(otherDiscRowNo, otherDiscColumnNo, player1.getDiscColor());

                // オセロ盤面表示
                board.display();

                // ターン終了
                player1.setTurn(false);
            }

            // プレイヤー2のターン中は以下ループ
            player2.setTurn(true);
            while (player2.getTrun()) {
                System.out.println("=====" + player2.getPlayerName() + "さんのターン！" + "=====");

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
                otherDiscColor = board.getOtherDiscColor(player2.getDiscColor());

                // 指定色コマ方角特定
                otherDiscRowNoColumnNo = board.seekDiscPos(rowNo2int, columnNo2int, otherDiscColor);
                otherDiscRowNo = otherDiscRowNoColumnNo.get(0);
                otherDiscColumnNo = otherDiscRowNoColumnNo.get(1);

                // 別コマ隣接チェック
                if (otherDiscRowNo == -1 && otherDiscColumnNo == -1) {
                    System.out.println("引っくり返せるコマがありません。");

                    // コマ配置取消
                    board.setDisc(rowNo2int, columnNo2int, "-");

                    continue;
                }

                // コマ配置(隣接している別コマをひっくり返す)
                board.setDisc(otherDiscRowNo, otherDiscColumnNo, player2.getDiscColor());

                // オセロ盤面表示
                board.display();

                // ターン終了
                player2.setTurn(false);
            }
        }
    }
}
