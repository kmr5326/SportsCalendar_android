package SportsCalendar;

import com.google.gson.annotations.SerializedName;

public class KboSchedule {
    @SerializedName("_id")
    private String this_id;
    @SerializedName("game1")
    private String game1;
    @SerializedName("game2")
    private String game2;
    @SerializedName("game3")
    private String game3;
    @SerializedName("game4")
    private String game4;
    @SerializedName("game5")
    private String game5;
    @SerializedName("dataCnt")
    private int dataCnt;

    public String getThis_id() {
        return this_id;
    }

    public void setThis_id(String this_id) {
        this.this_id = this_id;
    }

    public String getGame1() {
        return game1;
    }

    public void setGame1(String game1) {
        this.game1 = game1;
    }

    public String getGame2() {
        return game2;
    }

    public void setGame2(String game2) {
        this.game2 = game2;
    }

    public String getGame3() {
        return game3;
    }

    public void setGame3(String game3) {
        this.game3 = game3;
    }

    public String getGame4() {
        return game4;
    }

    public void setGame4(String game4) {
        this.game4 = game4;
    }

    public String getGame5() {
        return game5;
    }

    public void setGame5(String game5) {
        this.game5 = game5;
    }

    public int getDataCnt() {
        return dataCnt;
    }

    public void setDataCnt(int dataCnt) {
        this.dataCnt = dataCnt;
    }

    @Override
    public String toString() {
        return this_id + "\n" +
                game1 + "\n" +
                game2 + "\n" +
                game3 + "\n" +
                game4 + "\n" +
                game5 + "\n";
    }

}
