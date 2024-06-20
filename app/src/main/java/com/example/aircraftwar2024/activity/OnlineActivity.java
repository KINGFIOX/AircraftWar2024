package com.example.aircraftwar2024.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024.R;
import com.example.aircraftwar2024.web.WebSocketService;
import com.example.aircraftwar2024.web.structs.GameBegin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// 选择难度阶段
public class OnlineActivity extends AppCompatActivity {

    private static final String TAG = "OnlineActivity";
    boolean soundSwitch;
    private ActivityManager activityManager;

    class GameModeListener implements View.OnClickListener {

        int gameType;

        public GameModeListener(int gameMode) {
            this.gameType = gameMode;
        }

        @Override
        public void onClick(View view) {
            // 显示 alert
            AlertDialog.Builder builder = new AlertDialog.Builder(OnlineActivity.this);
            builder.setMessage("Matching, please wait...").setCancelable(false);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();

            // 使用 ExecutorService 在后台线程中执行任务
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.getMainLooper());

            executor.execute(() -> {
                try {
                    // 创立连接
                    WebSocketService.getInstance().connect("ws://10.249.12.73:9999");

                    // 阻塞线程，等待 gameBegin 返回
                    GameBegin gameBegin = WebSocketService.getInstance().recvBegin();

                    handler.post(() -> {
                        alertDialog.dismiss();
                        /* ---------- 切换 ---------- */
                        Intent intent = new Intent(OnlineActivity.this, OfflineGameActivity.class);
                        intent.putExtra("gameType", gameType);
                        intent.putExtra("soundOn", getIntent().getBooleanExtra("soundSwitch", false));
                        startActivity(intent);
                        finish();
                    });
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                    handler.post(() -> {
                        alertDialog.dismiss();
                        // 在这里可以处理连接或接收失败的逻辑
                        Toast.makeText(OnlineActivity.this, "Failed to connect or receive data", Toast.LENGTH_SHORT).show();
                    });
                }
            });
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