package com.example.aircraftwar2024.web.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aircraftwar2024.activity.ActivityManager;
import com.example.aircraftwar2024.activity.RankListActivity;
import com.example.aircraftwar2024.web.WebSocketService;
import com.example.aircraftwar2024.web.online_game.OnlineBaseGame;
import com.example.aircraftwar2024.web.online_game.OnlineEasyGame;
import com.example.aircraftwar2024.web.online_game.OnlineHardGame;
import com.example.aircraftwar2024.web.online_game.OnlineMediumGame;


public class OnlineGameActivity extends AppCompatActivity {
    private static final String TAG = "OnlineGameActivity";

    private int gameType = 0;

    public static Handler mHandler;

    public static boolean soundOn;

    /* ---------- 设置 width 和 height ---------- */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.getInstance().addActivity(OnlineGameActivity.this);

        getScreenHW();

        if (getIntent() != null) {
            gameType = getIntent().getIntExtra("gameType", 1);
            soundOn = getIntent().getBooleanExtra("soundOn", false);
            Log.v("GAMEAC", String.valueOf(soundOn));
        }


        /*TODO:根据用户选择的难度加载相应的游戏界面*/
        Log.v("GAME", "LOADING GAME");

        OnlineBaseGame baseGameView = getGameByModeID(gameType);
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

                    // 发送结束通知，并且发送 score
                    try {
                        WebSocketService.getInstance().sendGameEnd(score);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    // 切换到 Online end wait 页面
                    Intent intent = new Intent(OnlineGameActivity.this, OnlineEndActivity.class);

//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("gameType", gameType);
                    intent.putExtra("score", score);
//                    setContentView(R.layout.activity_record);
                    startActivity(intent);
                }

            }
        };
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

    /* ---------- 设置 Game Mode ---------- */
    public OnlineBaseGame getGameByModeID(int gameType) {
        switch (gameType) {
            case 0:
                return new OnlineEasyGame(OnlineGameActivity.this);
            case 1:
                return new OnlineMediumGame(OnlineGameActivity.this);
            case 2:
                return new OnlineHardGame(OnlineGameActivity.this);
            default:
                return new OnlineMediumGame(OnlineGameActivity.this);
        }
    }


    /* ---------- 按下 back ---------- */
    boolean backPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (backPressedOnce) {
            ActivityManager.getInstance().exitApp(OnlineGameActivity.this);
        }
        backPressedOnce = true;
        Toast.makeText(OnlineGameActivity.this, "Click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            backPressedOnce = false;
        }, 2000);
    }

}