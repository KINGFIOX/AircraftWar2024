package com.example.aircraftwar2024.web.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024.activity.ActivityManager;
import com.example.aircraftwar2024.web.WebSocketService;

// 选择难度阶段
public class OnlineEndActivity extends AppCompatActivity {

    int gameType = getIntent().getIntExtra("gameType", 1);
    int score = getIntent().getIntExtra("score", -1);

    // TODO 这个 activity 要加上一个 view

    /**
     * @brief 这相当于是模板函数
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO 接受 intent 的 score
    }

    @Override
    public void onBackPressed() {
        ActivityManager.getInstance().popActivity();
//        super.onBackPressed();
    }
}