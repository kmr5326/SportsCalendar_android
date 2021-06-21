package SportsCalendar;

import com.google.gson.annotations.SerializedName;

public class Write {
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;

    public Write(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
