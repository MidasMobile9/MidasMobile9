package com.test.midasmobile9.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.test.midasmobile9.R;
import com.test.midasmobile9.activity.AdminActivity;
import com.test.midasmobile9.data.AdminCoffeeOrderItem;
import com.test.midasmobile9.network.NetworkDefineConstant;
import com.test.midasmobile9.util.ParseHotCold;
import com.test.midasmobile9.util.ParseServerState;
import com.test.midasmobile9.util.ParseSize;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class OrderRecyclerAdapter extends RecyclerView.Adapter<OrderRecyclerAdapter.OrderViewHolder> {
    public static final String PROFILE_URL_HEADER = NetworkDefineConstant.HOST_URL + "/profileimg/";

    Context context = null;
    RecyclerView.LayoutManager layoutManager = null;

    List<AdminCoffeeOrderItem> items = new ArrayList<>();

    public OrderRecyclerAdapter(Context context, RecyclerView.LayoutManager layoutManager) {
        this.context = context;
        this.layoutManager = layoutManager;
    }

    @NonNull
    @Override
    public OrderRecyclerAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coffee_order, parent, false);

        return new OrderViewHolder(parent.getContext(), view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderRecyclerAdapter.OrderViewHolder holder, int position) {
        AdminCoffeeOrderItem item = items.get(position);

        // 고객 프로필
        Glide.with(context)
                .load(PROFILE_URL_HEADER + item.getProfileimg())
                .into(holder.circleImageViewCustomerImage);
        // 고객 닉네임/부서
        String customerInfo = item.getNickname() + "/" + item.getPart();
        holder.textViewCustomerInfo.setText(customerInfo);
        // 주문한 커피 이름
        holder.textViewCoffeeName.setText(item.getName());
        // 주문 수량
        holder.textViewOrderCount.setText(String.valueOf(item.getCount()));
        // 주문 사이즈
        holder.textViewOrderSize.setText(ParseSize.getSize(item.getSize()));
        // 주문 HOT/COLD
        holder.textViewOrderHotCold.setText(ParseHotCold.getHotCold(item.getHotcold()));
        // 주문 상태
        holder.textViewOrderStatus.setText(ParseServerState.getState(item.getState()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(AdminCoffeeOrderItem item) {
        items.add(item);
    }

    public void addNewItem(AdminCoffeeOrderItem item) {
        items.add(0, item);

        // 애니메이션
        notifyItemInserted(0);

        layoutManager.scrollToPosition(0);
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        Context context = null;
        View view = null;

        @BindView(R.id.circleImageViewCustomerImage)
        CircleImageView circleImageViewCustomerImage;
        @BindView(R.id.textViewCustomerInfo)
        TextView textViewCustomerInfo;
        @BindView(R.id.textViewCoffeeName)
        TextView textViewCoffeeName;
        @BindView(R.id.textViewOrderCount)
        TextView textViewOrderCount;
        @BindView(R.id.textViewOrderSize)
        TextView textViewOrderSize;
        @BindView(R.id.textViewOrderHotCold)
        TextView textViewOrderHotCold;
        @BindView(R.id.textViewOrderStatus)
        TextView textViewOrderStatus;
        @BindView(R.id.imageViewOrderPopMenu)
        ImageView imageViewOrderPopMenu;

        public OrderViewHolder(Context context, View itemView) {
            super(itemView);

            // 버터나이프
            ButterKnife.bind(this, itemView);

            if ( context != null ) {
                this.context = context;
            }

            if ( itemView != null ) {
                this.view = itemView;
            }
        }

        @OnClick(R.id.imageViewOrderPopMenu)
        public void onClickOrderPopMenu(View view) {
            // 팝업메뉴 생성(두번째 인자 : 팝업메뉴가 보여질 앵커)
            PopupMenu popupMenu = new PopupMenu(context, imageViewOrderPopMenu);
            // 팝업메뉴 인플레이션
            ((AdminActivity)context).getMenuInflater().inflate(R.menu.menu_admin_order_popup, popupMenu.getMenu());
            // 팝업메뉴 클릭 이벤트 처리
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    AdminCoffeeOrderItem selectedItem = items.get(getAdapterPosition());

                    switch ( item.getItemId() ) {
                        case R.id.popup_order_confirm_before:
                            textViewOrderStatus.setText("주문 확인 전");
                            break;
                        case R.id.popup_order_confirm_ok:
                            textViewOrderStatus.setText("주문 확인");
                            break;
                        case R.id.popup_order_creating:
                            textViewOrderStatus.setText("음료 준비 중");
                            break;
                        case R.id.popup_order_creation_complete:
                            textViewOrderStatus.setText("음료 준비 완료");
                            break;
                        case R.id.popup_order_take_out_complete:
                            int removeIndex = getAdapterPosition();

                            notifyItemRemoved(removeIndex);
                            items.remove(removeIndex);
                            break;
                        default:
                            break;
                    }
                    return true;
                }
            });
            // 팝업메뉴 보이기
            popupMenu.show();
        }
    }
}
