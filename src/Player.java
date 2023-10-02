public class Player {
    /** 
     * プレイヤー名
     */
    private String playerName;

    /**
     * コマの色
     */
    private String discColor;

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

    public void setDiscColor(String discColor) {
        this.discColor = discColor;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    // Getter
    public String getPlayerName() {
        return this.playerName;
    }

    public String getDiscColor() {
        return this.discColor;
    }

    public Boolean getTrun() {
        return this.turn;
    }
}
