public class Player {
    /** 
     * プレイヤー名
     */
    private String playerName;

    /**
     * コマ色
     */
    private String disc;

    /**
     * パス判定
     */
    private boolean pass;

    /**
     * ターン判定
     */
    private boolean turn;

    // setter
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    // getter
    public String getPlayerName() {
        return this.playerName;
    }

    public String getDisc() {
        return this.disc;
    }

    public Boolean getTrun() {
        return this.turn;
    }
}
