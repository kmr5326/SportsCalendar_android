package SportsCalendar;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import SportsCalendar.R;

public class SportsCalendarViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;

    public SportsCalendarViewHolder(Context context, View itemView) {
        super(itemView);

        textView = itemView.findViewById(R.id.textView);
    }

}
