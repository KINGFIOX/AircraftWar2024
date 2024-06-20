package com.example.aircraftwar2024.web.online_game;

import android.content.Context;

import com.example.aircraftwar2024.ImageManager;
import com.example.aircraftwar2024.aircraft.AbstractEnemyAircraft;

import java.util.LinkedList;
import java.util.List;


public class OnlineEasyGame extends OnlineBaseGame {

    public OnlineEasyGame(Context context) {
        super(context);
        this.backGround = ImageManager.BACKGROUND1_IMAGE;
        this.enemyMaxNumber = 2;
    }

    @Override
    protected void tick() {
    }

    /**
     * 简单模式没有 boss
     *
     */
    @Override
    protected List<AbstractEnemyAircraft> produceBoss() {
        return new LinkedList<>();
    }


}
