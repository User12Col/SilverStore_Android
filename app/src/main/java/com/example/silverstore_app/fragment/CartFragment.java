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
import com.example.silverstore_app.adapter.ItemCartAdapter;
import com.example.silverstore_app.api.ApiService;
import com.example.silverstore_app.model.Account;
import com.example.silverstore_app.model.Cart;
import com.example.silverstore_app.sharePreference.DataLocalManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CartFragment extends Fragment {

    private View view;
    private ItemCartAdapter itemCartAdapter;
    private List<Cart> itemCartList;
    private RecyclerView rclCart;
    private Disposable disposable;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        reference();
        Account acc = DataLocalManager.getAccount();
        getCartByID(acc.getAccID());
        //getAllCart();

        return view;
    }

    private void reference(){
        rclCart = view.findViewById(R.id.rclCart);

        itemCartList = new ArrayList<>();

        itemCartAdapter = new ItemCartAdapter(this);

    }

    private void getAllCart(){
        ApiService.apiService.callApiCart()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Cart>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<Cart> carts) {
                        itemCartList = carts;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        loadRclCart(itemCartList, itemCartAdapter);
                    }
                });
    }

    private void getCartByID(int accID){
        ApiService.apiService.callApiCartByID(accID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Cart>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull List<Cart> carts) {
                        itemCartList = carts;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        loadRclCart(itemCartList, itemCartAdapter);
                    }
                });
    }

    private void loadRclCart(List<Cart> itemList, ItemCartAdapter adapter){
        FragmentActivity cartFragment = getActivity();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(cartFragment, RecyclerView.VERTICAL, false);
        rclCart.setLayoutManager(linearLayoutManager);
        adapter.setData(itemList);
        rclCart.setAdapter(adapter);

    }

    private void reLoadData(List<Cart> itemList, ItemCartAdapter adapter){
        loadRclCart(itemList, adapter);
    }

    @Override
    public void onResume() {
        reLoadData(itemCartList, itemCartAdapter);
        super.onResume();
    }
}