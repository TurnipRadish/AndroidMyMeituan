package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

public class SharedPreferenceSaveData {
    public static final String SHARED_DATA_NAME = "142_data";
    // 保存QQ账号和登录密码到data.xml文件中
    public static boolean saveUserInfo(Context context, String account,
                                       String password) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_DATA_NAME, Context.MODE_PRIVATE);
        String oldAccount = sp.getString(account, null);
        if (oldAccount != null) {
            return false;
        }
        else {
            SharedPreferences.Editor edit = sp.edit();
            edit.putString(account, password);
            // 使用 apply 替代 commit 以实现异步提交，提高性能
            edit.apply();
            return true;
        }
    }
    // 从data.xml文件中获取存储的所有QQ账号和密码
    public static Map<String, String> getUserInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences(SHARED_DATA_NAME, Context.MODE_PRIVATE);
        Map<String, String> userMap = new HashMap<>();
        // 获取所有存储的数据
        Map<String, ?> allEntries = sp.getAll(); 
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getValue() instanceof String) {
                userMap.put(entry.getKey(), (String) entry.getValue());
            }
        }
        return userMap;
    }
}

