package SportsCalendar;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardViewHolder> {
    private ArrayList<PostItem> arrayList;//아마 양식 바꿔야할듯. 그 하나 파일 만들어서...

    public BoardAdapter() {
        arrayList = new ArrayList<>();
    }

    @Override
    public BoardViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.post_item, parent, false);
        BoardViewHolder boardViewHolder = new BoardViewHolder(context, view);

        return boardViewHolder;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public void onBindViewHolder(BoardViewHolder holder, int position) {
        PostItem postItem = arrayList.get(position);

        holder.post_title.setText(postItem.getPost_title());
        holder.post_date_create.setText(postItem.getPost_create());

    }

    public void setArrayData(PostItem strData) {
        arrayList.add(strData);
    }


    public class BoardViewHolder extends RecyclerView.ViewHolder {
        public TextView post_title, post_date_create;

        public BoardViewHolder(Context context, View itemView) {
            super(itemView);

            post_title = itemView.findViewById(R.id.post_title);
            post_date_create = itemView.findViewById(R.id.post_date_create);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Log.d("리사이클러뷰", Integer.toString(pos) + "클릭!");
                        Log.d("리사이클러뷰2", arrayList.get(pos).getPost_content());
                        arrayList.get(pos).getPost_content();
                        Intent intent = new Intent(v.getContext(),BoardSeeActivity.class);
                        intent.putExtra("SeePostTitle", arrayList.get(pos).getPost_title());
                        intent.putExtra("SeePostContent",arrayList.get(pos).getPost_content());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        v.getContext().startActivity(intent);


                    }
                }
            });
        }
    }

}
