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
    private Disposable disposable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reference();
        getAllAccount();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = txtUserName.getText().toString();
                String password = txtPassword.getText().toString();
                if(checkLogin(userName, password)){
                    Account acc = getAccountLogin(userName,password);
                    DataLocalManager.setAccount(acc);
                    Intent intent = new Intent(MainActivity.this, StoreActivity.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Dang nhap thanh cong", Toast.LENGTH_SHORT).show();

                } else{
                    Toast.makeText(MainActivity.this, "Sai thong tin dang nhap", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void reference(){
        txtUserName = findViewById(R.id.txtUserName);
        txtPassword = findViewById(R.id.txtPassword);

        btnLogin = findViewById(R.id.btnLogin);
        btnExit = findViewById(R.id.btnExit);

        listAccount = new ArrayList<>();
    }


    //read json from API
    private void getAllAccount(){
        ApiService.apiService.callApiAccont()
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
                        Toast.makeText(MainActivity.this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //check account to login
    private boolean checkLogin(String userName, String password){
        for(int i =0;i<listAccount.size();i++){
            Account account = listAccount.get(i);
            if(account.getUserName().equals(userName) && account.getPassword().equals(password)){
                return true;
            }
        }
        return false;

    }

    private Account getAccountLogin(String userName, String pass){
        for(int i =0;i<listAccount.size();i++){
            Account account = listAccount.get(i);
            if(account.getUserName().equals(userName) && account.getPassword().equals(pass)){
                return account;
            }
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(disposable !=null){
            disposable.dispose();
        }
    }
}