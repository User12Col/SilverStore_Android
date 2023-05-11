package com.example.silverstore_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MakeOrderActivity extends AppCompatActivity {

    private TextView txtName, txtPhone, txtAddress, txtEmail, txtTotalPrice;
    private Button btnMakeOrder;
    private RecyclerView rclOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_order);
    }

    private void reference(){
        txtName = findViewById(R.id.txtFullName);
        txtAddress = findViewById(R.id.txtAddress);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);

        btnMakeOrder = findViewById(R.id.btnMakeOrder);

        rclOrder = findViewById(R.id.rclOrder);
    }
}