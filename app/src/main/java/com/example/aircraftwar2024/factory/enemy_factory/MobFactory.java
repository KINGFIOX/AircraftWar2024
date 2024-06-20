package com.example.aircraftwar2024.factory.enemy_factory;

import com.example.aircraftwar2024.ImageManager;
import com.example.aircraftwar2024.activity.OfflineGameActivity;
import com.example.aircraftwar2024.aircraft.AbstractEnemyAircraft;
import com.example.aircraftwar2024.aircraft.MobEnemy;

public class MobFactory implements EnemyFactory {

    private int mobHp = 30;
    private int speedY = 10;

    @Override
    public AbstractEnemyAircraft createEnemyAircraft(double level) {
        return new MobEnemy(
                (int) ( Math.random() * (OfflineGameActivity.screenWidth - ImageManager.MOB_ENEMY_IMAGE.getWidth())),
                (int) (Math.random() * OfflineGameActivity.screenHeight * 0.05),
                0,
                (int) (this.speedY * level),
                (int) (this.mobHp * level));
    }

}
