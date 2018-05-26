package com.test.midasmobile9.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.test.midasmobile9.R;
import com.test.midasmobile9.activity.AdminActivity;
import com.test.midasmobile9.activity.CustomerProfileManagerActivity;
import com.test.midasmobile9.activity.MenuEditActivity;
import com.test.midasmobile9.data.AdminMenuItem;
import com.test.midasmobile9.data.CustomerInfoItem;
import com.test.midasmobile9.model.CustomerModel;
import com.test.midasmobile9.model.MenuInfoModel;
import com.test.midasmobile9.network.NetworkDefineConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerRecyclerAdapter extends RecyclerView.Adapter<CustomerRecyclerAdapter.CustomerViewHolder> {
    public static final String PROFILE_URL_HEADER = NetworkDefineConstant.HOST_URL + "/profileimg/";

    Context context = null;
    RecyclerView.LayoutManager layoutManager = null;

    List<CustomerInfoItem> items = new ArrayList<>();

    public CustomerRecyclerAdapter(Context context, RecyclerView.LayoutManager layoutManager) {
        this.context = context;
        this.layoutManager = layoutManager;
    }

    @NonNull
    @Override
    public CustomerRecyclerAdapter.CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer, parent, false);

        return new CustomerViewHolder(parent.getContext(), view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerRecyclerAdapter.CustomerViewHolder holder, int position) {
        CustomerInfoItem item = items.get(position);

        // 고객 프로필
        Glide.with(context)
                .load(PROFILE_URL_HEADER + item.getProfileimg())
                .into(holder.circleImageViewCustomerImage);
        // 고객 정보
        String customerInfo = item.getNickname() + "/" + item.getPart();
        holder.textViewCustomerInfo.setText(customerInfo);
        // 고객 전화번호
        holder.textViewCustomerPhone.setText(item.getPhone());
        // 고객 이메일
        holder.textViewCustomerEmail.setText(item.getEmail());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(CustomerInfoItem item) {
        items.add(item);
    }

    public void addNewItem(CustomerInfoItem item) {
        items.add(0, item);

        // 애니메이션
        notifyItemInserted(0);

        layoutManager.scrollToPosition(0);
    }

    public void clearItems() {
        items.clear();
    }

    public class CustomerViewHolder extends RecyclerView.ViewHolder {
        Context context = null;
        View view = null;

        @BindView(R.id.circleImageViewCustomerImage)
        CircleImageView circleImageViewCustomerImage;
        @BindView(R.id.textViewCustomerInfo)
        TextView textViewCustomerInfo;
        @BindView(R.id.textViewCustomerPhone)
        TextView textViewCustomerPhone;
        @BindView(R.id.textViewCustomerEmail)
        TextView textViewCustomerEmail;
        @BindView(R.id.imageViewCustomerPopMenu)
        ImageView imageViewCustomerPopMenu;

        public CustomerViewHolder(Context context, View itemView) {
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

        @OnClick(R.id.imageViewCustomerPopMenu)
        public void onClickCustomerPopMenu(View view) {
            // 팝업메뉴 생성(두번째 인자 : 팝업메뉴가 보여질 앵커)
            PopupMenu popupMenu = new PopupMenu(context, imageViewCustomerPopMenu);
            // 팝업메뉴 인플레이션
            ((AdminActivity)context).getMenuInflater().inflate(R.menu.menu_admin_customer_popup, popupMenu.getMenu());
            // 팝업메뉴 클릭 이벤트 처리
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    CustomerInfoItem selectedItem = items.get(getAdapterPosition());
                    final int no = selectedItem.getNo();
                    final int position = getAdapterPosition();

                    switch ( item.getItemId() ) {
                        case R.id.popup_customer_modify:
                            // 메뉴 수정 액티비티 띄우기
                            Intent intent = new Intent((context), CustomerProfileManagerActivity.class);
                            intent.putExtra("editCustomerItem", selectedItem);
                            context.startActivity(intent);

                            break;
                        case R.id.popup_customer_delete:
                            AlertDialog.Builder ab = new AlertDialog.Builder(context);
                            ab.setMessage("고객정보를 삭제하시겠습니까?");
                            ab.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new CustomerDeleteTask().execute(no, position);
                                }
                            });
                            ab.setNegativeButton("취소", null);
                            ab.show();
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

    public class CustomerDeleteTask extends AsyncTask<Integer, Void, Map<String, Object>> {
        int position = -1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Map<String, Object> doInBackground(Integer... params) {
            int no = params[0];
            position = params[1];

            Map<String, Object> map = CustomerModel.deleteCustomer(no);
            return map;
        }

        @Override
        protected void onPostExecute(Map<String, Object> map) {
            super.onPostExecute(map);

            if ( map == null ) {
                String message = "인터넷 연결이 원활하지 않습니다. 잠시후 다시 시도해주세요.";
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            } else {
                // 통신성공
                boolean result = false;
                String message = null;

                if ( map.containsKey("result") ) {
                    result = (boolean)map.get("result");
                }

                if ( map.containsKey("message") ) {
                    message = (String)map.get("message");
                }

                if ( result ) {
                    // 메뉴 삭제 성공
                    Toast.makeText(context, "고객정보가 삭제되었습니다.", Toast.LENGTH_SHORT).show();

                    notifyItemRemoved(position);
                    items.remove(position);
                } else {
                    // 메뉴 삭제 실패
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                }
            }
        }
    }
}
