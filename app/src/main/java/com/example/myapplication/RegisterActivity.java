package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    DatabaseManager databaseManager;

    EditText etAccount;
    EditText etPassword;
    Button btnRegister;
    TextView btnGotoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        databaseManager = DatabaseManager.getInstance(this);

        // 绑定视图
        setBindView();
    }

    void setBindView() {
        etAccount = findViewById(R.id.et_account);
        etPassword = findViewById(R.id.et_password);

        btnRegister = findViewById(R.id.btn_register);
        // 设置注册按钮的点击事件监听器
        btnRegister.setOnClickListener(v -> {
            // 获取用户输入的账号和密码
            String account = etAccount.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            // 注册
            register(account, password);
        });

        btnGotoLogin = findViewById(R.id.tv_goto_login);
        // 设置返回事件
        btnGotoLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    boolean verify(String account, String password) {
        if (account.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return false;
        } else if (account.length() <= 6) {
            Toast.makeText(RegisterActivity.this, "用户名长度需大于6", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.isEmpty()) {
            Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        } else if (password.length() <= 8) {
            Toast.makeText(RegisterActivity.this, "密码长度需大于8", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    void register(String account, String password) {
        if (!verify(account, password)) {
            return;
        }

        if (databaseManager.isUsernameExists(account)){
            Toast.makeText(RegisterActivity.this, "注册失败：该用户名已被注册", Toast.LENGTH_SHORT).show();
            return;
        }

        // 插入新用户数据
        long newRowId = databaseManager.insertUser(account, password);
        if (newRowId != -1) {
            // 数据库写入成功，尝试写入 SharedPreferences
            boolean isSaveSuccess = SharedPreferenceSaveData.saveUserInfo(RegisterActivity.this, account, password);
            if (isSaveSuccess) {
                // 注册成功后直接跳转到主界面
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
            } else {
                // 写入 SharedPreferences 失败
                Toast.makeText(RegisterActivity.this, "注册失败：保存用户信息到本地失败", Toast.LENGTH_SHORT).show();
            }
        } else {
            // 数据库写入失败
            Toast.makeText(RegisterActivity.this, "注册失败：保存用户信息到数据库失败", Toast.LENGTH_SHORT).show();
        }
    }
}