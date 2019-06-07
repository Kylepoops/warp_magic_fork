package com.eclipsekingdom.warpmagic.effect;

import org.bukkit.entity.Player;

public class EffectManager {

    private EffectManager(){}

    public static final EffectManager getInstance(){
        return EFFECT_MANAGER_INSTANCE;
    }


    public void playEffect(Player player){

    }



    private static final EffectManager EFFECT_MANAGER_INSTANCE = new EffectManager();

}
