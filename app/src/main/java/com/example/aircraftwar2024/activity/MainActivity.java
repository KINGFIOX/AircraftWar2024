/**
 * @brief 主界面
 * 包括：开始游戏 + 声音开关
 */
package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.offline.activity.OfflineActivity;
import com.example.aircraftwar2024.web.activity.OnlineActivity;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().addActivity(MainActivity.this);

        // 初始化
        getScreenHW();

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
            ActivityManager.getInstance().exitApp(MainActivity.this);
        }
        backPressedOnce = true;
        Toast.makeText(MainActivity.this, "Click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            backPressedOnce = false;
        }, 2000);
    }

    /* ---------- 设置 width 和 height ---------- */
    public static int screenWidth, screenHeight;

    public void getScreenHW() {
        // 定义DisplayMetrics 对象
        DisplayMetrics dm = new DisplayMetrics();
        // 取得窗口属性
        getDisplay().getRealMetrics(dm);

        // 窗口的宽度
        screenWidth = dm.widthPixels;
        // 窗口高度
        screenHeight = dm.heightPixels;

        Log.i(TAG, "screenWidth : " + screenWidth + " screenHeight : " + screenHeight);
    }
}
