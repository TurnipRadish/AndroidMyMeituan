package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PaymentActivity extends AppCompatActivity {
    Button btnConfirmPayment;
    TextView tvTotalPrice;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        // 获取总价钱
        String totalPrice = getIntent().getStringExtra("totalPrice");
        tvTotalPrice = findViewById(R.id.tv_total_price);
        tvTotalPrice.setText("¥" + totalPrice);

        // 确认付钱按钮
//        btnConfirmPayment = findViewById(R.id.btn_confirm_payment);
//        btnConfirmPayment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 这里可以添加支付逻辑
//            }
//        });

        // 返回按钮
        btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(PaymentActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        });
    }
}