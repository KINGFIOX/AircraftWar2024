package com.example.aircraftwar2024.web.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024.activity.ActivityManager;
import com.example.aircraftwar2024.web.WebSocketService;

// 选择难度阶段
public class OnlineEndActivity extends AppCompatActivity {

    // 接受了来自上一个 Intent 的内容
    int gameType = getIntent().getIntExtra("gameType", 1);
    int score = getIntent().getIntExtra("score", -1);

    // TODO 这个 activity 要加上一个 view

    /**
     * @brief 这相当于是模板函数
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO : 阻塞获取来自 server 的内容，同时要显示: 更新 对手的分数
    }

    @Override
    public void onBackPressed() {
        ActivityManager.getInstance().popActivity();
//        super.onBackPressed();
    }
}