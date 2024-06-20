package com.example.aircraftwar2024.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024.R;

// 选择难度阶段
public class OnlineActivity extends AppCompatActivity {
    boolean soundSwitch;
    private ActivityManager activityManager;

    class GameModeListener implements View.OnClickListener {

        int gameType;
        public GameModeListener(int gameMode) {
            this.gameType = gameMode;
        }
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(OnlineActivity.this, OfflineGameActivity.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("gameType", gameType);
            intent.putExtra("soundOn", getIntent().getBooleanExtra("soundSwitch", false));

            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityManager = ActivityManager.getActivityManager();
        activityManager.addActivity(OnlineActivity.this);
        setContentView(R.layout.activity_offline);
        Button easyModeButton = (Button) findViewById(R.id.easyModeButton);
        Button normalModeButton = (Button) findViewById(R.id.normalModeButton);
        Button hardModeButton = (Button) findViewById(R.id.hardModeButton);
        this.soundSwitch = getIntent().getBooleanExtra("soundSwitch", false);
        easyModeButton.setOnClickListener(new GameModeListener(0));
        normalModeButton.setOnClickListener(new GameModeListener(1));
        hardModeButton.setOnClickListener(new GameModeListener(2));
    }

    @Override
    public void onBackPressed() {
        activityManager.finishActivity();
//        super.onBackPressed();
    }
}