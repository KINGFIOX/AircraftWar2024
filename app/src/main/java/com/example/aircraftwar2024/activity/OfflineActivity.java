package com.example.aircraftwar2024.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aircraftwar2024.R;

// 选择难度阶段
public class OfflineActivity extends AppCompatActivity {
    boolean soundSwitch;

    /**
     * @brief GameModeListener 在按下的瞬间开始 Socket
     */
    class GameModeListener implements View.OnClickListener {

        int gameType;

        public GameModeListener(int gameMode) {
            this.gameType = gameMode;
        }

        @Override
        public void onClick(View view) {
            /* ---------- 下面是切换页面 ---------- */
            Intent intent = new Intent(OfflineActivity.this, OfflineGameActivity.class);
            intent.putExtra("gameType", gameType);
            intent.putExtra("soundOn", getIntent().getBooleanExtra("soundSwitch", false));
            startActivity(intent);  // 跳转到游戏 activity
            finish();
        }
    }

    /**
     * @brief 这相当于是模板函数
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getActivityManager().addActivity(OfflineActivity.this);
        setContentView(R.layout.activity_offline);

        this.soundSwitch = getIntent().getBooleanExtra("soundSwitch", false);

        Button easyModeButton = (Button) findViewById(R.id.easyModeButton);
        easyModeButton.setOnClickListener(new GameModeListener(0));

        Button normalModeButton = (Button) findViewById(R.id.normalModeButton);
        normalModeButton.setOnClickListener(new GameModeListener(1));

        Button hardModeButton = (Button) findViewById(R.id.hardModeButton);
        hardModeButton.setOnClickListener(new GameModeListener(2));

    }

    @Override
    public void onBackPressed() {
        ActivityManager.getActivityManager().finishActivity();
//        super.onBackPressed();
    }
}