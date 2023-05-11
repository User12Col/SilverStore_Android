package com.example.silverstore_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.silverstore_app.R;
import com.example.silverstore_app.model.Cart;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemOrderAdapter extends RecyclerView.Adapter<ItemOrderAdapter.ItemOrderViewHolder>{

    private Context context;
    private List<Cart> cartList;

    public ItemOrderAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Cart> list){
        this.cartList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderitem_layout,parent,false);
        return new ItemOrderAdapter.ItemOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemOrderViewHolder holder, int position) {
        Cart item = cartList.get(position);
        if(item == null){
            return;
        }
        holder.txtOrderItemName.setText(item.getProName());
        //holder.txtQuantity.setText();
    }

    @Override
    public int getItemCount() {
        if(cartList!=null){
            return cartList.size();
        }
        return 0;
    }

    public class ItemOrderViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgOrderItem;
        private TextView txtOrderItemName, txtQuantity, txtOrderItemPrice;
        public ItemOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            imgOrderItem = itemView.findViewById(R.id.imgOrderItem);
            txtOrderItemName = itemView.findViewById(R.id.txtOrderItemName);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtOrderItemPrice = itemView.findViewById(R.id.txtPriceItem);
        }
    }

}
