package com.test.midasmobile9.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.test.midasmobile9.R;
import com.test.midasmobile9.data.CoffeeOrderItem;
import com.test.midasmobile9.network.NetworkDefineConstantOSH;
import com.test.midasmobile9.util.ParseHotCold;
import com.test.midasmobile9.util.ParseServerState;
import com.test.midasmobile9.util.ParseSize;

import java.util.ArrayList;

public class UserLookupMenuRecyclerAdapter extends RecyclerView.Adapter<UserLookupMenuRecyclerAdapter.ViewHolder> {
    private ArrayList<CoffeeOrderItem> userLookupOrderItemList;
    private Activity activity;

    public class ViewHolder extends RecyclerView.ViewHolder {
        //리사이클러뷰에 적용되는 뷰그룹에 들어가는 여러가지 요소들을 여기서 정의
        public ImageView userMenuLookupCardImageView;
        public TextView userMenuLookupCardTitleTextView;
        public TextView userMenuLookupCardStateTextView;
        public TextView userMenuLookupCardSizeTemperCountTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            userMenuLookupCardImageView = itemView.findViewById(R.id.userMenuLookupCardImageView);
            userMenuLookupCardStateTextView = itemView.findViewById(R.id.userMenuLookupCardStateTextView);
            userMenuLookupCardTitleTextView = itemView.findViewById(R.id.userMenuLookupCardTitleTextView);
            userMenuLookupCardSizeTemperCountTextView = itemView.findViewById(R.id.userMenuLookupCardSizeTemperCountTextView);
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
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull UserLookupMenuRecyclerAdapter.ViewHolder holder, int position) {
        String sizeTemperCountStr = userLookupOrderItemList.get(position).getCount() + "잔 / " +
                ParseSize.getSize(userLookupOrderItemList.get(position).getSize()) + " / " +
                ParseHotCold.getHotCold(userLookupOrderItemList.get(position).getHotcold());
        holder.userMenuLookupCardTitleTextView.setText(userLookupOrderItemList.get(position).getName());
        holder.userMenuLookupCardStateTextView.setText(ParseServerState.getState(userLookupOrderItemList.get(position).getState()));
        holder.userMenuLookupCardSizeTemperCountTextView.setText(sizeTemperCountStr);
        Glide.with(activity)
                .load(NetworkDefineConstantOSH.SERVER_URL_GET_MENU_IMG + userLookupOrderItemList.get(position).getImg()) // 이미지 URL 주소
                .into(holder.userMenuLookupCardImageView);
        //Glide.with(activity)
        //       .load(R.drawable.ic_coffee_24dp)
        //        .into(holder.userMenuLookupCardImageView);
    }

    @Override
    public int getItemCount() {
        return userLookupOrderItemList.size();
    }
}
