package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText et_account; //账号输入框
    private EditText et_password; //密码输入框
    private Button btn_login; //登录按钮
    private TextView btn_goto_register; // 注册按钮
    private TextView btn_goto_user_list; // 切换账号链接

    private Map<String, String> userInf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置当前内容视图
        setContentView(R.layout.activity_main);

        // 获取已有的用户数据
        userInf = SharedPreferenceSaveData.getUserInfo(this);

        // 绑定视图对象
        setBindView();

        // 自动填充信息
        autoFill(userInf);
    }

    private ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            if (data != null) {
                String selectedUser = data.getStringExtra("selectedUser");
                String selectedPassword = data.getStringExtra("selectedPassword");
                et_account.setText(selectedUser);
                //et_password.setText(selectedPassword);
            }
        }
    });

    void autoFill(Map<String, String> userInf) {
        if (userInf != null && !userInf.isEmpty()) {
            Map.Entry<String, String> firstEntry = userInf.entrySet().iterator().next();
            et_account.setText(firstEntry.getKey());
            //et_password.setText(firstEntry.getValue());
        }
    }

    void setBindView() {
        // 绑定账号输入框
        et_account = findViewById(R.id.et_account);
        // 板顶密码输入框
        et_password = findViewById(R.id.et_password);

        // 绑定登录按钮
        btn_login = findViewById(R.id.btn_login);
        // 设置登录按钮点击事件
        btn_login.setOnClickListener(v -> {
            //当点击登录按钮时，获取界面上输入的QQ账号和密码
            String account = et_account.getText().toString().trim();
            String password = et_password.getText().toString();

            login(account, password);
        });

        // 绑定注册跳转链接文字
        btn_goto_register = findViewById(R.id.tv_goto_register);
        // 设置注册连接跳转文字点击事件
        btn_goto_register.setOnClickListener(v -> {
            // 创建一个Intent对象，用于跳转到注册界面
            android.content.Intent intent = new android.content.Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        btn_goto_user_list = findViewById(R.id.tv_goto_user_list);
        if (userInf != null && userInf.size() > 1) {
            btn_goto_user_list.setVisibility(View.VISIBLE);
            // 设置切换账号链接点击事件
            btn_goto_user_list.setOnClickListener(v -> {
                Intent intent = new Intent(this, UserSelectActivity.class);
                intent.putExtra("userInf", (java.io.Serializable) userInf);
                startForResult.launch(intent);
            });
        } else {
            btn_goto_user_list.setVisibility(View.GONE);
        }
    }

    void login(String account, String password) {
        //检验输入的账号和密码是否为空
        if (TextUtils.isEmpty(account)) {
            Toast.makeText(this, "请输入QQ账号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }

        // 输出账号密码，用于调试
        Log.i("test", "当前用户=" + account);
        Log.i("test", "输入的密码=" +password);

        if (!DatabaseManager.getInstance(this).isUsernameExists(account)) {
            Toast.makeText(this, "不存在该用户", Toast.LENGTH_SHORT).show();
        } else {
           if (!DatabaseManager.getInstance(this).checkUserPassword(account, password)) {
               Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
            } else {
                // 记录到 sharedPreference 中
                SharedPreferenceSaveData.saveUserInfo(this, account, password);
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                Intent intentToMenu = new Intent(LoginActivity.this, MenuActivity.class);
                startActivity(intentToMenu);
            }
        }
    }
}
