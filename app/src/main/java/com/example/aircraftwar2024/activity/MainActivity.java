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

import com.example.aircraftwar2024.GameWebSocketClient;
import com.example.aircraftwar2024.R;

import java.net.URI;
import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {

    private ActivityManager activityManager;
    boolean backPressedOnce = false;

    private GameWebSocketClient webSocketClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化ActivityManager并将当前活动添加到管理器中
        activityManager = ActivityManager.getActivityManager();
        activityManager.addActivity(MainActivity.this);

        // 设置当前活动的视图为activity_main布局
        setContentView(R.layout.activity_main);

        // 获取布局中的控件
        RadioButton soundOn = (RadioButton) findViewById(R.id.soundOn);
        Button startButton = (Button) findViewById(R.id.startButton);

        // 为开始按钮设置点击监听器
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OfflineActivity.class);
                intent.putExtra("soundSwitch", soundOn.isChecked());
                startActivity(intent);
            }
        });

        // 为连接按钮设置监听器
        Button onlineButton = (Button) findViewById(R.id.onlineButton);
        onlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 显示匹配中
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Matching, please wait...").setCancelable(false);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                try {
                    webSocketClient = GameWebSocketClient.getInstance("ws://10.249.12.73:9999");
                    webSocketClient.connect();

                    // 在新线程中等待 "begin" 消息
                    new Thread(() -> {
                        try {
                            webSocketClient.awaitBegin();
                            // await 结束后，结束
                            runOnUiThread(() -> {
                                alertDialog.dismiss();
                                Toast.makeText(MainActivity.this, "Connected to server and game started",
                                        Toast.LENGTH_SHORT).show();

                                /* ---------- 开始游戏 ---------- */
                                Intent intent = new Intent(MainActivity.this, OnlineGameActivity.class);
                                intent.putExtra("soundSwitch", soundOn.isChecked());
                                startActivity(intent);
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }).start();

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (backPressedOnce) {
            activityManager.exitApp(MainActivity.this);
        }
        backPressedOnce = true;
        Toast.makeText(MainActivity.this, "Click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            backPressedOnce = false;
        }, 2000);
    }
}
