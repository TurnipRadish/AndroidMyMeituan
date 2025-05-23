package com.example.myapplication;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;

public class MenuActivity extends AppCompatActivity {
    private HashMap<String, List<Dish>> menuList = new HashMap<>();
    private DatabaseHelper dbHelper;  // 新增数据库帮助类实例
    private DatabaseHelper dbHelper1;
    private SQLiteDatabase db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        SQLiteStudioService.instance().start(this);
        dbHelper = new DatabaseHelper(this, "meituan.db", null, 1);
        db = dbHelper.getWritableDatabase();

        // 测试模式下生成测试数据
        boolean isTestMode = true;  // 测试标志
        if (isTestMode) {
            dbHelper.generateTestData(10);
        }
        // 初始化数据库帮助类
        //dbHelper = new DatabaseHelper(this);

        // 从数据库读取数据（替换原硬编码数组）
        menuList.put("1", dbHelper.getDishesByCategory("1"));  // 推荐类别
        menuList.put("2", dbHelper.getDishesByCategory("2"));  // 必买类别
    }

    private List<Dish> createFoodList(String[] names, String[] prices, int[] imgs, String[] sales) {
        List<Dish> list = new ArrayList<>();
        for(int i = 0; i < names.length; i++){
            Dish bean = new Dish();
            bean.setName(names[i]);
            bean.setPrice(prices[i]);
            bean.setImg(imgs[i]);
            bean.setSales(sales[i]);
            list.add(bean);
        }
        return list;
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

    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 关闭数据库连接
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}
