package com.example.silverstore_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.silverstore_app.R;
import com.example.silverstore_app.api.IClickItemProductListener;
import com.example.silverstore_app.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ItemProductAdapter extends RecyclerView.Adapter<ItemProductAdapter.ItemProductHolder> implements Filterable {

    private Fragment fragment;
    private List<Product> itemProductList;
    private List<Product> itemProductListOld;
    private IClickItemProductListener iClickItemProductListener;

    public ItemProductAdapter(Fragment fragment, IClickItemProductListener iClickItemProductListener) {
        this.fragment = fragment;
        this.iClickItemProductListener = iClickItemProductListener;
    }

    public void setData(List<Product> list){
        this.itemProductList = list;
        this.itemProductListOld = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productitem_layout,parent,false);
        return new ItemProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemProductHolder holder, int position) {
        Product item = itemProductList.get(position);
        if(item == null){
            return;
        }
        holder.txtProName.setText(item.getProName());
        holder.txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));

        Picasso.get()
                .load(item.getProIMG())
                .placeholder(R.drawable.lock_42px)
                .error(R.drawable.search_42px)
                .into(holder.proImg);

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemProductListener.onClickItemProduct(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(itemProductList !=null)
        {
            return itemProductList.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String txtSearch = charSequence.toString();
                if(txtSearch.isEmpty()){
                    itemProductList = itemProductListOld;
                } else{
                    List<Product> list = new ArrayList<>();
                    for(Product pro: itemProductListOld){
                        if(pro.getProName().toLowerCase().contains(txtSearch.toLowerCase())){
                            list.add(pro);
                        }
                    }

                    itemProductList = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = itemProductList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                itemProductList = (List<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ItemProductHolder extends RecyclerView.ViewHolder {

        private ImageView proImg;
        private TextView txtProName, txtUnitPrice;
        private LinearLayout layout;
        public ItemProductHolder(@NonNull View itemView) {
            super(itemView);
            proImg = itemView.findViewById(R.id.imgProduct);
            txtProName = itemView.findViewById(R.id.txtProName);
            txtUnitPrice = itemView.findViewById(R.id.txtUnitPrice);
            layout = itemView.findViewById(R.id.itemProductLayout);

        }
    }
}
