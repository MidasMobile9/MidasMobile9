package com.test.midasmobile9.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.midasmobile9.R;
import com.test.midasmobile9.data.CoffeeOrderItem;

import java.util.ArrayList;

public class UserLookupMenuRecyclerAdapter extends RecyclerView.Adapter<UserLookupMenuRecyclerAdapter.ViewHolder> {
    private ArrayList<CoffeeOrderItem> userLookupOrderItemList;
    private Activity activity;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        //리사이클러뷰에 적용되는 뷰그룹에 들어가는 여러가지 요소들을 여기서 정의
        public ImageView userMenuLookupCardImageView;
        public TextView userMenuLookupCardTitleTextView;
        public TextView userMenuLookupCardStateTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            userMenuLookupCardImageView = itemView.findViewById(R.id.userMenuLookupCardImageView);
            userMenuLookupCardStateTextView = itemView.findViewById(R.id.userMenuLookupCardStateTextView);
            userMenuLookupCardTitleTextView = itemView.findViewById(R.id.userMenuLookupCardTitleTextView);
        }
    }

    public UserLookupMenuRecyclerAdapter(ArrayList<CoffeeOrderItem> userLookupOrderItemList, Activity activity) {
        this.userLookupOrderItemList = userLookupOrderItemList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public UserLookupMenuRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_lookup, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull UserLookupMenuRecyclerAdapter.ViewHolder holder, int position) {
        holder.userMenuLookupCardTitleTextView.setText(userLookupOrderItemList.get(position).getName());
        holder.userMenuLookupCardStateTextView.setText(userLookupOrderItemList.get(position).getState());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
