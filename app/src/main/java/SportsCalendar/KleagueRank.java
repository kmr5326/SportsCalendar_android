package SportsCalendar;

import com.google.gson.annotations.SerializedName;

public class KleagueRank {
    @SerializedName("id")
    private String id;
    @SerializedName("rank")
    private int rank ;
    @SerializedName("team")
    private String team;
    @SerializedName("gameCnt")
    private int gameCnt;
    @SerializedName("point")
    private int point;
    @SerializedName("win")
    private int win;
    @SerializedName("draw")
    private int draw;
    @SerializedName("lose")
    private int lose;
    @SerializedName("goal")
    private int goal;
    @SerializedName("lost")
    private int lost;

    public int getGameCnt() {
        return gameCnt;
    }

    public void setGameCnt(int gameCnt) {
        this.gameCnt = gameCnt;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

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
}
