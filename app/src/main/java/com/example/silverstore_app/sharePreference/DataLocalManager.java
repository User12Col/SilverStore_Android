package com.example.silverstore_app.sharePreference;

import android.content.Context;
import android.provider.ContactsContract;

import com.example.silverstore_app.model.Account;
import com.google.gson.Gson;

public class DataLocalManager {

    private static final String PREF_OBJECT_ACCOUNT = "PREF_OBJECT_ACCOUNT";
    private static DataLocalManager instance;

    private mSharePreference mySharePreference;

    public static void init(Context context){
        instance =  new DataLocalManager();
        instance.mySharePreference = new mSharePreference(context);
    }

    public static DataLocalManager getInstance(){
        if(instance == null){
            instance = new DataLocalManager();
        }

        return instance;
    }

    public static void setAccount(Account account){
        Gson gson = new Gson();
        String jsonAcc = gson.toJson(account);
        DataLocalManager.getInstance().mySharePreference.putStringValue(PREF_OBJECT_ACCOUNT, jsonAcc);
    }

    public static Account getAccount(){
        String jsonAcc = DataLocalManager.getInstance().mySharePreference.getStringValue(PREF_OBJECT_ACCOUNT);
        Gson gson = new Gson();
        Account acc = gson.fromJson(jsonAcc, Account.class);
        return acc;
    }
}
