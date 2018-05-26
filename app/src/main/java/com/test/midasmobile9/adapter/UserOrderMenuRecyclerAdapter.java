package com.test.midasmobile9.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.test.midasmobile9.R;
import com.test.midasmobile9.activity.MainActivity;
import com.test.midasmobile9.activity.UserOrderOptionActivity;
import com.test.midasmobile9.data.CoffeeMenuItem;

import java.util.ArrayList;

/**
 * 유저가 메뉴를 고르기 위한 창에 존재하는 리사이클러뷰에 할당해주는 어댑터
 * (UserOrderFragment -> userOrderMenuRecyclerView
 */
public class UserOrderMenuRecyclerAdapter extends RecyclerView.Adapter<UserOrderMenuRecyclerAdapter.ViewHolder> {

    private ArrayList<CoffeeMenuItem> userOrderMenuItemList;
    private Activity activity;

    public class ViewHolder extends RecyclerView.ViewHolder{
        //리사이클러뷰에 적용되는 뷰그룹에 들어가는 여러가지 요소들을 여기서 정의
        public ImageView userMenuOrderCardImageView;
        public TextView userMenuOrderCardTitleTextView;
        public TextView userMenuOrderCardPriceTextView;

        public CardView userMenuOrderCard;

        public ViewHolder(View itemView) {
            super(itemView);

            userMenuOrderCardImageView = itemView.findViewById(R.id.userMenuOrderCardImageView);
            userMenuOrderCardPriceTextView = itemView.findViewById(R.id.userMenuOrderCardPriceTextView);
            userMenuOrderCardTitleTextView = itemView.findViewById(R.id.userMenuOrderCardTitleTextView);
            userMenuOrderCard = itemView.findViewById(R.id.userMenuOrderCard);

            userMenuOrderCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent coffeeIntent = new Intent(activity, UserOrderOptionActivity.class);
                    coffeeIntent.putExtra(UserOrderOptionActivity.COFFEE_MENU_ITEM_EXTRA_NAME, userOrderMenuItemList.get(getAdapterPosition()));
                    coffeeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    activity.startActivityForResult(coffeeIntent, MainActivity.MENU_ORDER_OPTION_REQUEST_CODE);
                }
            });
        }
    }

    public UserOrderMenuRecyclerAdapter(ArrayList<CoffeeMenuItem> userOrderMenuItemList, Activity activity) {
        this.userOrderMenuItemList = userOrderMenuItemList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public UserOrderMenuRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_menu_order, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull UserOrderMenuRecyclerAdapter.ViewHolder holder, int position) {
        holder.userMenuOrderCardTitleTextView.setText(userOrderMenuItemList.get(position).getName());
        holder.userMenuOrderCardPriceTextView.setText(String.format("%s 원", userOrderMenuItemList.get(position).getPrice()));
        //Glide.with(activity)
        //        .load(URL_IMAGE + userOrderMenuItemList.get(position).getImg()) // 이미지 URL 주소
        //        .into(userMenuOrderCardImageView);
        Glide.with(activity)
                .load(R.drawable.ic_coffee_24dp)
                .into(holder.userMenuOrderCardImageView);
    }

    @Override
    public int getItemCount() {
        return userOrderMenuItemList.size();
    }
}
