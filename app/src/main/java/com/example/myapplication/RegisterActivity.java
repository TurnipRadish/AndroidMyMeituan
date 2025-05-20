package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    EditText etAccount;
    EditText etPassword;
    Button btnRegister;
    TextView btnGotoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        // 获取视图
        etAccount = findViewById(R.id.et_account);
        etPassword = findViewById(R.id.et_password);
        btnRegister = findViewById(R.id.btn_register);
        btnGotoLogin = findViewById(R.id.tv_goto_login);

        // 设置注册按钮的点击事件监听器
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取用户输入的账号和密码
                String account = etAccount.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // 简单验证输入是否为空
                if (account.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                }
                if (password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                }

                // 这里可以添加注册逻辑，例如保存到本地或发送到服务器
                // 示例：使用 SPSaveQQ 类保存用户信息
//                boolean isSaveSuccess = SharedPreferenceSaveData.saveUserInfo(RegisterActivity.this, account, password);
                boolean isSaveSuccess = SharedPreferenceSaveData.saveUserInfo(RegisterActivity.this, account, password);
                if (isSaveSuccess) {
                    // 注册成功后直接跳转到主界面
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败：该用户名已被注册", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // 设置返回事件
        btnGotoLogin.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }
}