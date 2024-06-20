package com.example.aircraftwar2024.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024.offline_game.OfflineBaseGame;
import com.example.aircraftwar2024.offline_game.OfflineEasyGame;
import com.example.aircraftwar2024.offline_game.OfflineHardGame;
import com.example.aircraftwar2024.offline_game.OfflineMediumGame;


public class OfflineGameActivity extends AppCompatActivity {
    private static final String TAG = "OfflineGameActivity";

    protected int gameType = 0;

    public static Handler mHandler;
//    private int score;

    public static boolean soundOn;

    /* ---------- 设置 width 和 height ---------- */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getActivityManager().addActivity(OfflineGameActivity.this);

        if (getIntent() != null) {
            gameType = getIntent().getIntExtra("gameType", 1);
            soundOn = getIntent().getBooleanExtra("soundOn", false);
            Log.v("GAMEAC", String.valueOf(soundOn));
        }


        /*TODO:根据用户选择的难度加载相应的游戏界面*/
        Log.v("GAME", "LOADING GAME");

        OfflineBaseGame baseGameView = getGameByModeID(gameType);
        //baseGameView.setSoundOn(soundOn);
        Log.v("GAME", "HAVE LOADED GAME");
        setContentView(baseGameView);

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                // FIXME 飞机死亡，发送消息
                if (msg.what == 1) {
                    int score = baseGameView.getScore();

                    Intent intent = new Intent(OfflineGameActivity.this, RankListActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("gameType", gameType);
                    intent.putExtra("score", score);
//                    setContentView(R.layout.activity_record);
                    startActivity(intent);
                }

            }
        };
    }

    /* ---------- 设置 Game Mode ---------- */
    public OfflineBaseGame getGameByModeID(int gameType) {
        switch (gameType) {
            case 0:
                return new OfflineEasyGame(OfflineGameActivity.this);
            case 1:
                return new OfflineMediumGame(OfflineGameActivity.this);
            case 2:
                return new OfflineHardGame(OfflineGameActivity.this);
            default:
                return new OfflineMediumGame(OfflineGameActivity.this);
        }
    }


    /* ---------- 按下 back ---------- */
    boolean backPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (backPressedOnce) {
            ActivityManager.getActivityManager().exitApp(OfflineGameActivity.this);
        }
        backPressedOnce = true;
        Toast.makeText(OfflineGameActivity.this, "Click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            backPressedOnce = false;
        }, 2000);
    }

}