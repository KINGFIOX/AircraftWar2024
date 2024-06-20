/**
 * @brief 主界面
 * 包括：开始游戏 + 声音开关
 */
package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.aircraftwar2024.R;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getActivityManager().addActivity(MainActivity.this);

        // 设置当前 activity 的 布局
        setContentView(R.layout.activity_main);

        // 获取布局中的控件
        RadioButton soundOn = (RadioButton) findViewById(R.id.soundOn);

        /* ---------- offline ---------- */
        Button startButton = (Button) findViewById(R.id.startButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OfflineActivity.class);
                intent.putExtra("soundSwitch", soundOn.isChecked());  // 会给下一个页面传入 sountSwitch 的 value
                startActivity(intent);
            }
        });

        /* ---------- online ---------- */
        Button onlineButton = (Button) findViewById(R.id.onlineButton);
        onlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OnlineActivity.class);
                intent.putExtra("soundSwitch", soundOn.isChecked());
                startActivity(intent);
            }
        });
    }

    /* ---------- 按下 back ---------- */
    boolean backPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (backPressedOnce) {
            ActivityManager.getActivityManager().exitApp(MainActivity.this);
        }
        backPressedOnce = true;
        Toast.makeText(MainActivity.this, "Click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            backPressedOnce = false;
        }, 2000);
    }
}
