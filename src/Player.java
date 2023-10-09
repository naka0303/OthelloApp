public class Player {
    /** 
     * プレイヤー名
     */
    private String playerName;

    /**
     * コマ色
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

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    // getter
    public String getPlayerName() {
        return this.playerName;
    }

    public String getDiscColor() {
        return this.discColor;
    }

    public Boolean getPass() {
        return this.pass;
    }

    public Boolean getTrun() {
        return this.turn;
    }
}
