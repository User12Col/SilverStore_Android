package com.example.silverstore_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.silverstore_app.R;
import com.example.silverstore_app.model.Cart;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemCartAdapter extends RecyclerView.Adapter<ItemCartAdapter.ItemCartViewHolder>{

    private Fragment fragment;
    private List<Cart> cartList;

    public ItemCartAdapter(Fragment fragment){
        this.fragment = fragment;
    }

    public void setData(List<Cart> list){
        this.cartList = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ItemCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cartitem_layout,parent,false);
        return new ItemCartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemCartViewHolder holder, int position) {
        Cart item = cartList.get(position);
        if(item == null){
            return;
        }

        holder.txtProNameCart.setText(item.getProName());
        holder.txtProPriceCart.setText(String.valueOf(item.getUnitPrice()));
        Picasso.get()
                .load(item.getProIMG())
                .placeholder(R.drawable.lock_42px)
                .error(R.drawable.search_42px)
                .into(holder.imgProCart);

        holder.btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(holder.txtQuantityCart.getText().toString());
                if(quantity >=1){
                    int rs = quantity - 1;
                    holder.txtQuantityCart.setText(String.valueOf(rs));
                }

            }
        });

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantity = Integer.parseInt(holder.txtQuantityCart.getText().toString());
                if(quantity >=1){
                    int rs = quantity + 1;
                    holder.txtQuantityCart.setText(String.valueOf(rs));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(cartList!=null){
            return cartList.size();
        }
        return 0;
    }

    public class ItemCartViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProCart;
        private TextView txtProNameCart, txtProPriceCart;
        private ImageView btnPlus, btnSub;
        private EditText txtQuantityCart;
        public ItemCartViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProCart = itemView.findViewById(R.id.imgProCart);
            txtProNameCart = itemView.findViewById(R.id.txtProNameCart);
            txtProPriceCart = itemView.findViewById(R.id.txtProPriceCart);

            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnSub = itemView.findViewById(R.id.btnSub);

            txtQuantityCart = itemView.findViewById(R.id.txtPriceCart);
        }
    }
}
