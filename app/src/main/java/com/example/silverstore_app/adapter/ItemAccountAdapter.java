package com.example.silverstore_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.silverstore_app.R;
import com.example.silverstore_app.model.Account;
import com.example.silverstore_app.model.ItemAccount;

import java.util.List;

public class ItemAccountAdapter extends RecyclerView.Adapter<ItemAccountAdapter.ItemAccountHolder>{

    private Fragment fragment;
    private List<ItemAccount> itemAccountList;

    public ItemAccountAdapter(Fragment fragment) {
        this.fragment = fragment;

    }

    //set data for adapter
    public void setData(List<ItemAccount> list){
        this.itemAccountList = list;
        notifyDataSetChanged();
    }

    //convert file itemlayout to view
    @NonNull
    @Override
    public ItemAccountHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.useritem_layout, parent, false);
        return new ItemAccountHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAccountHolder holder, int position) {
        ItemAccount itemAccount = itemAccountList.get(position);
        if(itemAccount == null){
            return;
        }

        holder.imgItem.setImageResource(itemAccount.getImg());
        holder.txtTitle.setText(itemAccount.getTitle());

    }

    //return number of row
    @Override
    public int getItemCount() {
        if(itemAccountList !=null){
            return itemAccountList.size();
        }
        return 0;
    }


    //create class holder to store view from itemlayout
    public class ItemAccountHolder extends RecyclerView.ViewHolder {

        private ImageView imgItem;
        private TextView txtTitle;

        public ItemAccountHolder(@NonNull View itemView) {
            super(itemView);

            imgItem = itemView.findViewById(R.id.imgItem);
            txtTitle = itemView.findViewById(R.id.txtTitle);

        }
    }
}
