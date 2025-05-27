package com.example.myapplication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService;


public class MainActivity extends AppCompatActivity {
    DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();
    }

    void init() {
        Log.d("test", "项目初始化开始");

        // 同步本地预制数据库
        syncLocalDataIfNeed(false);

        databaseManager = DatabaseManager.getInstance(this);

        Intent toLogin = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(toLogin);
        Log.d("test", "项目初始化结束");
    }

    void syncLocalDataIfNeed(boolean forceFlag) {
        if (!forceFlag && getDatabasePath("meituan1.db").exists()) {
            Log.d("test", "设备内存在数据库");
        }
        else {
            Log.d("test", "设备内无数据库");

            try {
                InputStream inputStream = getAssets().open("meituan1.db");
                File outFile = new File(getApplicationInfo().dataDir + "/databases/meituan1.db");
                File parentDir = outFile.getParentFile();
                if (parentDir == null || !parentDir.exists()) {
                    parentDir.mkdirs();
                }
                OutputStream outputStream = new FileOutputStream(outFile);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }

                outputStream.flush();
                outputStream.close();
                inputStream.close();
            } catch (java.io.IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "数据库文件同步失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}


