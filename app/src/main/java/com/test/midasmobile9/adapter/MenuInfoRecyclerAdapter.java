package com.test.midasmobile9.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.test.midasmobile9.R;
import com.test.midasmobile9.data.AdminCoffeeOrderItem;
import com.test.midasmobile9.data.AdminMenuItem;

import java.util.ArrayList;
import java.util.List;

public class MenuInfoRecyclerAdapter extends RecyclerView.Adapter<MenuInfoRecyclerAdapter.MenuViewHolder> {

    Context context = null;
    RecyclerView.LayoutManager layoutManager = null;

    List<AdminMenuItem> items = new ArrayList<>();

    public MenuInfoRecyclerAdapter(Context context, RecyclerView.LayoutManager layoutManager) {
        this.context = context;
        this.layoutManager = layoutManager;
    }

    @NonNull
    @Override
    public MenuInfoRecyclerAdapter.MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_coffee_menu, parent, false);

        return new MenuViewHolder(parent.getContext(), view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuInfoRecyclerAdapter.MenuViewHolder holder, int position) {
        AdminMenuItem item = items.get(position);

        //
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
