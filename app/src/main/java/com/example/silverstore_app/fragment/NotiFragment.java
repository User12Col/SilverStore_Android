package com.example.silverstore_app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.silverstore_app.R;
import com.example.silverstore_app.StoreActivity;
import com.example.silverstore_app.adapter.ItemNotiAdapter;
import com.example.silverstore_app.adapter.ItemNotiAdapter;
import com.example.silverstore_app.api.ApiService;
import com.example.silverstore_app.model.Account;
import com.example.silverstore_app.model.Notification;
import com.example.silverstore_app.sharePreference.DataLocalManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class NotiFragment extends Fragment {

    private View view;
    private ItemNotiAdapter itemNotiAdapter;
    private List<Notification> itemNotiList;
    private RecyclerView rclNoti;
    private Disposable disposable;
    public NotiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =inflater.inflate(R.layout.fragment_noti, container, false);
        reference();
        Account account = DataLocalManager.getAccount();
        getNotiByID(account.getAccID());
        return view;
    }

    @Override
    public void onResume() {
        reLoadData(itemNotiList, itemNotiAdapter);
        super.onResume();
    }

    private void reference(){
        itemNotiAdapter = new ItemNotiAdapter(this);
        rclNoti = view.findViewById(R.id.rclNoti);

        itemNotiList = new ArrayList<>();

    }

    private void getNoti(){
        ApiService.apiService.callApiNotification()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Notification>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<Notification> notifications) {
                        itemNotiList = notifications;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void getNotiByID(int accID){
        ApiService.apiService.callApiNotiByID(accID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Notification>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull List<Notification> notifications) {
                        itemNotiList = notifications;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        setRclNoti(itemNotiList,itemNotiAdapter);
                    }
                });
    }

    private void setRclNoti(List<Notification> list, ItemNotiAdapter itemAdapter){
        FragmentActivity notiFragment = getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(notiFragment, RecyclerView.VERTICAL, false);
        rclNoti.setLayoutManager(linearLayoutManager);
        itemAdapter.setData(list);
        rclNoti.setAdapter(itemAdapter);
    }

    private void reLoadData(List<Notification> list, ItemNotiAdapter itemAdapter){
        setRclNoti(list, itemAdapter);
    }

}