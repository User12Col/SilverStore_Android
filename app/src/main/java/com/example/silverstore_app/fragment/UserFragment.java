package com.example.silverstore_app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.silverstore_app.R;
import com.example.silverstore_app.StoreActivity;
import com.example.silverstore_app.adapter.ItemAccountAdapter;
import com.example.silverstore_app.model.Account;
import com.example.silverstore_app.model.ItemAccount;
import com.example.silverstore_app.sharePreference.DataLocalManager;

import java.util.ArrayList;
import java.util.List;
public class UserFragment extends Fragment {

    private TextView txtAccName;
    private View view;
    private RecyclerView listItemAccount;
    private ItemAccountAdapter itemAccountAdapter;
    private List<ItemAccount> list;
    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);
        reference();
        getAccount();
        loadRecycleView();
        return view;

    }

    private void reference(){
        txtAccName = view.findViewById(R.id.txtAccName);

        listItemAccount = view.findViewById(R.id.listItemAccount);
        itemAccountAdapter = new ItemAccountAdapter(this);

        list = new ArrayList<>();
        list.add(new ItemAccount("Thông tin tài khoản", R.drawable.admin_settings_male_42px));
        list.add(new ItemAccount("Đặt lại mật khẩu", R.drawable.lock_42px));
        list.add(new ItemAccount("Đơn hàng", R.drawable.shop_42px));
        list.add(new ItemAccount("Cài đặt", R.drawable.settings_42px));
    }

    //get account sended from Activity
    private void getAccount(){
        Account account = DataLocalManager.getAccount();
        txtAccName.setText(account.getUserName());
    }

    private void loadRecycleView(){
        FragmentActivity userFragment = getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(userFragment, RecyclerView.VERTICAL, false);
        listItemAccount.setLayoutManager(linearLayoutManager);

        itemAccountAdapter.setData(list);
        listItemAccount.setAdapter(itemAccountAdapter);
    }
}