package com.example.silverstore_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.silverstore_app.R;
import com.example.silverstore_app.model.Notification;

import java.util.List;

public class ItemNotiAdapter extends RecyclerView.Adapter<ItemNotiAdapter.ItemNotiHolder>{

    private Fragment fragment;
    private List<Notification> notiList;

    public ItemNotiAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setData(List<Notification> list){
        this.notiList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemNotiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notiitem_layout,parent,false);
        return new ItemNotiHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemNotiHolder holder, int position) {
        Notification item = notiList.get(position);
        if(item == null){
            return;
        }

        holder.txtNoti.setText(item.getContent());
        holder.txtDay.setText(item.getDate());
        holder.txtTime.setText(item.getTime());
    }

    @Override
    public int getItemCount() {
        if(notiList!=null){
            return notiList.size();
        }
        return 0;
    }

    public class ItemNotiHolder extends RecyclerView.ViewHolder{

        private TextView txtNoti, txtDay, txtTime;
        public ItemNotiHolder(@NonNull View itemView) {
            super(itemView);
            txtNoti = itemView.findViewById(R.id.txtNoti);
            txtDay = itemView.findViewById(R.id.txtDay);
            txtTime = itemView.findViewById(R.id.txtTime);
        }
    }
}
