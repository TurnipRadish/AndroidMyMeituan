package com.example.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class MenuActivity extends AppCompatActivity {
    public static double TotalPrice = 0.0;
    TextView tv_total_money;
    Button btn_checkout;

    DatabaseManager databaseManager;

    private HashMap<String, List<Dish>> menuList = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        databaseManager = DatabaseManager.getInstance(this);
        setBindView();
        SQLiteStudioService.instance().start(this);

        // 生成测试数据
        boolean isTestMode = true;  // 测试标志
        if (isTestMode) {
            databaseManager.generateTestData(10);
        }

        // 从数据库读取数据
        menuList.put("1", databaseManager.getDishesByCategory("1"));  // 推荐类别
        menuList.put("2", databaseManager.getDishesByCategory("2"));  // 必买类别
    }

    void setBindView() {
        tv_total_money = findViewById(R.id.tv_total_money);

        btn_checkout = findViewById(R.id.btn_checkout);
        btn_checkout.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, PaymentActivity.class);
            intent.putExtra("totalPrice", String.valueOf(TotalPrice));
            startActivity(intent);
        });
    }

    public void switchData(List<Dish> list) {
        // 创建带有新数据的右碎片
        MenuFragmentRight rightFragment = new MenuFragmentRight().getInstance(list);
        // 开启碎片事务，使用带新数据额右碎片替换改变右视图
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fc_right, rightFragment);
        // 提交事务
        fragmentTransaction.commit();
    }

    List<Dish> getMenuList(String key) {
        return menuList.get(key);
    }

    public void addTotalPrice(double price) {
        TotalPrice += price;
        tv_total_money.setText(String.valueOf(TotalPrice));
    }
}
