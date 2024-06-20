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

import com.example.aircraftwar2024.game.BaseGame;
import com.example.aircraftwar2024.game.EasyGame;
import com.example.aircraftwar2024.game.HardGame;
import com.example.aircraftwar2024.game.MediumGame;
import com.example.aircraftwar2024.game.OnlineGame;

public class OnlineGameActivity extends AppCompatActivity {
    private static final String TAG = "OnlineGameActivity";

    private int gameType = 0;
    public static int screenWidth, screenHeight;

    public static Handler mHandler;
    // private int score;

    public static boolean soundOn;

    boolean backPressedOnce = false;

    private ActivityManager activityManager;

    // public MyMediaPlayer myMediaPlayer = new
    // MyMediaPlayer(GameActivity.this,true);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityManager = ActivityManager.getActivityManager();
        activityManager.addActivity(OnlineGameActivity.this);

        getScreenHW();  // 获取屏幕的长宽

        if (getIntent() != null) {
            gameType = getIntent().getIntExtra("gameType", 1);
            soundOn = getIntent().getBooleanExtra("soundOn", false);
            Log.v("GAMEAC", String.valueOf(soundOn));
        }

        /* TODO:根据用户选择的难度加载相应的游戏界面 */
        Log.v("GAME", "LOADING GAME");

        // BaseGame baseGameView = getGameByModeID(gameType);
        OnlineGame onlineGameView = new OnlineGame(OnlineGameActivity.this);
        // baseGameView.setSoundOn(soundOn);
//        Log.v("GAME", "HAVE LOADED GAME");
        setContentView(onlineGameView);

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                // FIXME 飞机死亡，发送消息
                if (msg.what == 1) {
                    int score = onlineGameView.getScore();
                    Intent intent = new Intent(OnlineGameActivity.this, RankListActivity.class);

                    // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    // Intent.FLAG_ACTIVITY_NEW_TASK);
                    // 我们默认是 中等模式
                    intent.putExtra("gameType", 1);
                    intent.putExtra("score", score);
                    // setContentView(R.layout.activity_record);
                    startActivity(intent);  //
                }

            }
        };
    }

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

    @Override
    public void onBackPressed() {
        if (backPressedOnce) {
            activityManager.exitApp(OnlineGameActivity.this);
        }
        backPressedOnce = true;
        Toast.makeText(OnlineGameActivity.this, "Click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            backPressedOnce = false;
        }, 2000);
    }

}