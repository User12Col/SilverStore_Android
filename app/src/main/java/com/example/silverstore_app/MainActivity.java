package com.example.silverstore_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.silverstore_app.api.ApiService;
import com.example.silverstore_app.model.Account;
import com.example.silverstore_app.sharePreference.DataLocalManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private EditText txtUserName, txtPassword;
    private Button btnLogin, btnExit;
    private List<Account> listAccount;
    private Account acc;
    private Disposable disposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reference();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();
                checkLogin(userName, password);
            }
        });
    }


    private void reference(){
        txtUserName = findViewById(R.id.txtUserName);
        txtPassword = findViewById(R.id.txtPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnExit = findViewById(R.id.btnExit);

        listAccount = new ArrayList<>();

        acc = new Account();
    }

    private void checkLogin(String username, String pass){
        ApiService.apiService.callApiCheckLogin(username, pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Account>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(@NonNull List<Account> accounts) {
                        listAccount = accounts;

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        checkAcc(listAccount, username, pass);
                    }
                });
    }

    private void checkAcc(List<Account> list,String username, String pass){
        if(list.get(0).getUserName() !=null && list.get(0).getPassword()!=null){
            DataLocalManager.setAccount(list.get(0));
            Intent intent = new Intent(MainActivity.this, StoreActivity.class);
            startActivity(intent);
        } else{
            Toast.makeText(MainActivity.this, "Sai thong tin dang nhap", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(disposable !=null){
            disposable.dispose();
        }
    }
}