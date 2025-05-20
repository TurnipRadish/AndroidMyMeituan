package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private EditText et_account;   //账号输入框
    private EditText et_password; //密码输入框
    private Button btn_login;      //登录按钮
    private TextView btn_goto_register; // 注册按钮
    private Map<String, String> userInf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取视图
        EditText etAccount = findViewById(R.id.et_account);
        EditText etPassword = findViewById(R.id.et_password);
        Button btnLogin = findViewById(R.id.btn_login);

        // 设置登录按钮的点击事件监听器
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取用户输入的账号和密码
                String account = etAccount.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // 简单验证输入是否为空
                if (account.isEmpty()) {
                    Toast.makeText(MainActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 验证用户登录信息
                boolean isLoginSuccess = SharedPreferenceSaveData.verifyUserInfo(MainActivity.this, account, password);
                if (isLoginSuccess) {
                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    // 这里可以添加跳转到主界面的逻辑
                } else {
                    Toast.makeText(MainActivity.this, "登录失败：用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
        initView();
        userInf = SharedPreferenceSaveData.getUserInfo(this);
    }
    private void initView() {
        et_account = findViewById(R.id.et_account);
        et_password = findViewById(R.id.et_password);

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        btn_goto_register = findViewById(R.id.tv_goto_register);
        btn_goto_register.setOnClickListener(v -> {
            // 创建一个 Intent 对象，用于启动 RegisterActivity
            android.content.Intent intent = new android.content.Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_login) {
            //当点击登录按钮时，获取界面上输入的QQ账号和密码
            String account = et_account.getText().toString().trim();
            String password = et_password.getText().toString();
            //检验输入的账号和密码是否为空
            if (TextUtils.isEmpty(account)) {
                Toast.makeText(this, "请输入QQ账号", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                return;
            }

            Log.i(MainActivity.class.toString(), account);
            Log.i(MainActivity.class.toString(), password);

            if (userInf.get(account) == null) {
                Toast.makeText(this, "登录失败：账号不存在", Toast.LENGTH_SHORT).show();
            }
            else {
                if (userInf != null && userInf.containsKey(account) && userInf.get(account).equals(password)) {
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intentToMenu = new Intent(MainActivity.this, MenuActivity.class);
                    startActivity(intentToMenu);
                } else {
                    Toast.makeText(this, "登录失败：账户或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}


