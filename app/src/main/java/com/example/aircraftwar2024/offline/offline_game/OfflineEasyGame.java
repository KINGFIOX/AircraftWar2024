package com.example.aircraftwar2024.offline.offline_game;

import android.content.Context;

import com.example.aircraftwar2024.ImageManager;
import com.example.aircraftwar2024.aircraft.AbstractEnemyAircraft;

import java.util.LinkedList;
import java.util.List;


public class OfflineEasyGame extends OfflineBaseGame {

    public OfflineEasyGame(Context context) {
        super(context);
        this.backGround = ImageManager.BACKGROUND1_IMAGE;
        this.enemyMaxNumber = 2;
    }

    @Override
    protected void tick() {
    }

    /**
     * 简单模式没有 boss
     * @return
     */
    @Override
    protected List<AbstractEnemyAircraft> produceBoss() {
        return new LinkedList<>();
    }


}
