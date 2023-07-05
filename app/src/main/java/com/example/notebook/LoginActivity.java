package com.example.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextPassword;
    private Button buttonLogin;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);

        editTextName = findViewById(R.id.editTextName);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        // 读取作者信息
        String authorName = sharedPreferences.getString("authorName", "");
        if (authorName.isEmpty()) {
            // 如果作者信息不存在，则设置默认作者姓名
            authorName = "张三";
            // 存储默认作者姓名到SharedPreferences
            sharedPreferences.edit().putString("authorName", authorName).apply();
        }

        editTextName.setText(authorName);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String password = editTextPassword.getText().toString();

                // 检查用户是否注册过
                boolean isUserRegistered = sharedPreferences.contains(name);
                if (!isUserRegistered) {
                    // 如果用户没有注册过，则进行注册
                    sharedPreferences.edit().putString(name, password).apply();

                    // 注册成功，跳转到记事本界面
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // 用户注册过，检查用户名和密码是否匹配
                    String storedPassword = sharedPreferences.getString(name, "");
                    if (password.equals(storedPassword)) {
                        // 登录成功，跳转到记事本界面
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        // 登录失败，显示错误消息
                        Toast.makeText(LoginActivity.this, "用户名/密码错误", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
