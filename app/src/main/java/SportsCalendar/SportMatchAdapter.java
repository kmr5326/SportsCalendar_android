package SportsCalendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import SportsCalendar.R;

import java.util.ArrayList;

public class SportMatchAdapter extends RecyclerView.Adapter<SportsCalendarViewHolder> {
    private ArrayList<String> arrayList;

    public SportMatchAdapter(){
        arrayList = new ArrayList<>();
    }


    @Override
    public SportsCalendarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.match_schedule_item, parent, false);
        SportsCalendarViewHolder sportsCalendarViewHolder = new SportsCalendarViewHolder(context, view);

        return sportsCalendarViewHolder;
    }

    @Override
    public void onBindViewHolder(SportsCalendarViewHolder holder, int position) {
        String text = arrayList.get(position);
        holder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setArrayData(String strData) {
        arrayList.add(strData);
    }

}
