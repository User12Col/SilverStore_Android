package com.example.silverstore_app.api;

import com.example.silverstore_app.model.Account;
import com.example.silverstore_app.model.Cart;
import com.example.silverstore_app.model.Notification;
import com.example.silverstore_app.model.Product;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.internal.operators.flowable.FlowableScalarXMap;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    //http://192.168.1.146/APIAndroid/checkLogin.php

    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    OkHttpClient.Builder okBuilder = new OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(loggingInterceptor);

    ApiService apiService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.146/APIAndroid/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okBuilder.build())
            .build()
            .create(ApiService.class);


    @GET("api/account/read.php")
    Observable<List<Account>> callApiAccont();

    @GET("api/product/read.php")
    Observable<List<Product>> callApiProduct();

    @GET("api/cart/read.php")
    Observable<List<Cart>> callApiCart();

    @GET("api/notification/read.php")
    Observable<List<Notification>> callApiNotification();


    //http://localhost/APIAndroid/api/account/show.php?userName=loc@gmail.com&pass=123
    @GET("api/account/show.php")
    Observable<List<Account>> callApiCheckLogin(@Query("userName") String userName,
                                          @Query("pass") String pass);

    //http://localhost/APIAndroid/api/cart/show.php?accID=1
    @GET("api/cart/show.php")
    Observable<List<Cart>> callApiCartByID(@Query("accID") int accID);

    //http://localhost/APIAndroid/api/notification/show.php?accID=1
    @GET("api/notification/show.php")
    Observable<List<Notification>> callApiNotiByID(@Query("accID") int accID);

    //http://localhost/APIAndroid/api/cart/check.php?proID=1&accID=1
    @GET("api/cart/check.php")
    Observable<List<Cart>> callApiCheckCart(@Query("proID") int proID,
                                            @Query("accID") int accID);

    //http://localhost/APIAndroid/api/cart/create.php?proID=1&proName=Loc&proIMG=loc&unitPrice=1000&accID=1
    @POST("api/cart/create.php")
    Observable<String> callApiAddCart(@Query("proID") int proID,
                                      @Query("proName") String proName,
                                      @Query("proIMG") String proIMG,
                                      @Query("unitPrice") int unitPrice,
                                      @Query("accID") int accID);


}
