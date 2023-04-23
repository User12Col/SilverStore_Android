package com.example.silverstore_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.silverstore_app.api.ApiService;
import com.example.silverstore_app.model.Account;
import com.example.silverstore_app.model.Cart;
import com.example.silverstore_app.model.Notification;
import com.example.silverstore_app.model.Product;
import com.example.silverstore_app.sharePreference.DataLocalManager;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProductActivity extends AppCompatActivity {

    private TextView txtProNameDetail, txtPriceDetail, txtDis;
    private ImageView imgDetail;
    private Button btnAddToCart;
    private Product item;
    private List<Cart> cartList;
    private boolean check = false;
    private String result;
    private Disposable disposable;
    private Account acc = DataLocalManager.getAccount();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        reference();
        getProduct();

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(System.currentTimeMillis());

                SimpleDateFormat formatterTime= new SimpleDateFormat("HH:mm:ss");
                Date time = new Date(System.currentTimeMillis());

                Notification itemNoti = new Notification(0
                        , acc.getAccID()
                        ,item.getProName()+" was added to cart"
                        , formatter.format(date)
                        , formatterTime.format(time));

                checkCart(item.getProID(), acc.getAccID());

            }
        });
    }

    private void reference(){
        txtProNameDetail = findViewById(R.id.txtProNameDetail);
        txtPriceDetail = findViewById(R.id.txtProPriceDetail);
        txtDis = findViewById(R.id.txtDis);

        imgDetail = findViewById(R.id.imgProductDetail);

        btnAddToCart = findViewById(R.id.btnAddToCart);

        cartList = new ArrayList<>();
    }

    private void getProduct(){
        Intent intent = getIntent();
        item = (Product) intent.getSerializableExtra("product");

        txtProNameDetail.setText(item.getProName());
        txtPriceDetail.setText("$"+String.valueOf(item.getUnitPrice()));
        txtDis.setText(item.getDesciption());
        Picasso.get()
                .load(item.getProIMG())
                .placeholder(R.drawable.lock_42px)
                .error(R.drawable.search_42px)
                .into(imgDetail);
    }

    private void addCart(Cart cart){
        ApiService.apiService.callApiAddCart(cart.getProID(), cart.getProName(), cart.getProIMG(), cart.getUnitPrice(),cart.getAccID())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        result = s;

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        if(result.equals("Success")){
                            Toast.makeText(ProductActivity.this, "Add success", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void checkCart(int proID, int accID){
        ApiService.apiService.callApiCheckCart(proID,accID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Cart>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable =d;
                    }

                    @Override
                    public void onNext(@NonNull List<Cart> carts) {
                        cartList = carts;

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        if(cartList.size()!=0){
                            check = true;
                            Toast.makeText(ProductActivity.this, item.getProName() + " Already Add", Toast.LENGTH_SHORT).show();
                        } else{
                            check = false;
                            Cart cart = new Cart();
                            cart.setCartID(0);
                            cart.setProID(item.getProID());
                            cart.setProName(item.getProName());
                            cart.setProIMG(item.getProIMG());
                            cart.setUnitPrice(item.getUnitPrice());
                            cart.setAccID(acc.getAccID());
                            addCart(cart);
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(disposable !=null){
            disposable.dispose();
        }
    }
}