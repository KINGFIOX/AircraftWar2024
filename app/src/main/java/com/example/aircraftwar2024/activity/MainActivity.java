/**
 * @brief 主界面
 *  包括：开始游戏 + 声音开关
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

public class MainActivity extends AppCompatActivity {

    private ActivityManager activityManager;
    boolean backPressedOnce = false;
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
        Button onlineButton  = (Button) findViewById(R.id.onlineButton);
        onlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //显示匹配中
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Matching, please wait...").setCancelable(false);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                //创建子进程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        alertDialog.dismiss(); // 匹配完成后关闭对话框

                    }
                }).start();
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