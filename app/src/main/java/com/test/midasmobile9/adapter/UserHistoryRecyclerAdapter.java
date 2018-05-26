package com.test.midasmobile9.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.test.midasmobile9.R;
import com.test.midasmobile9.data.CoffeeHistoryItem;
import com.test.midasmobile9.util.ParseServerDatetime;

import java.util.ArrayList;

public class UserHistoryRecyclerAdapter extends RecyclerView.Adapter<UserHistoryRecyclerAdapter.ViewHolder>{
    private ArrayList<CoffeeHistoryItem> userHistoryItemList;
    private Activity activity;

    public class ViewHolder extends RecyclerView.ViewHolder{
        //리사이클러뷰에 적용되는 뷰그룹에 들어가는 여러가지 요소들을 여기서 정의
        public TextView userHistoryCardDateTextView;
        public TextView userHistoryCardMenuNameTextView;
        public TextView userHistoryCardQuantityTextView;
        public TextView userHistoryCardPriceTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            userHistoryCardDateTextView = itemView.findViewById(R.id.userHistoryCardDateTextView);
            userHistoryCardMenuNameTextView = itemView.findViewById(R.id.userHistoryCardMenuNameTextView);
            userHistoryCardQuantityTextView = itemView.findViewById(R.id.userHistoryCardQuantityTextView);
            userHistoryCardPriceTextView = itemView.findViewById(R.id.userHistoryCardPriceTextView);
        }
    }

    public UserHistoryRecyclerAdapter(ArrayList<CoffeeHistoryItem> userHistoryItemList, Activity activity) {
        this.userHistoryItemList = userHistoryItemList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public UserHistoryRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_history, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull UserHistoryRecyclerAdapter.ViewHolder holder, int position) {
        String datetime = userHistoryItemList.get(position).getDate();
        String year = ParseServerDatetime.getYear(datetime);
        String month = ParseServerDatetime.getMonth(datetime);
        String date = ParseServerDatetime.getDate(datetime);

        holder.userHistoryCardDateTextView.setText(String.format("%s.%s.%s", year, month, date));
        holder.userHistoryCardMenuNameTextView.setText(userHistoryItemList.get(position).getName());
        holder.userHistoryCardQuantityTextView.setText(userHistoryItemList.get(position).getCount());
        holder.userHistoryCardPriceTextView.setText(userHistoryItemList.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return userHistoryItemList.size();
    }
}
