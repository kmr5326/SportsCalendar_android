package SportsCalendar;

import com.google.gson.annotations.SerializedName;

public class KboRank {
    @SerializedName("id")
    private String id;
    @SerializedName("rank")
    private int rank ;
    @SerializedName("team")
    private String team;
    @SerializedName("gameCnt")
    private int gameCnt;
    @SerializedName("win")
    private int win;
    @SerializedName("lose")
    private int lose;
    @SerializedName("draw")
    private int draw;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getGameCnt() {
        return gameCnt;
    }

    public void setGameCnt(int gameCnt) {
        this.gameCnt = gameCnt;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }
}
