package com.eclipsekingdom.warpmagic.effect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class EffectManager {

    private EffectManager(){}

    public static final EffectManager getInstance(){
        return EFFECT_MANAGER_INSTANCE;
    }






    private static final EffectManager EFFECT_MANAGER_INSTANCE = new EffectManager();

    private final EffectDatabase effectDatabase = EffectDatabase.getInstance();

    private final HashMap<UUID, List<EffectDataType>> playerToEffects = new HashMap<>();
    private final HashMap<UUID, String> newPlayerToName = new HashMap<>();
    private final List<UUID> playersWithUnsavedData = new ArrayList<>();

    private void trackUnsavedData(UUID playerID){
        if(!playersWithUnsavedData.contains(playerID)){
            playersWithUnsavedData.add(playerID);
        }
    }

    private void resolveUnsavedData(UUID playerID){
        while (playersWithUnsavedData.contains(playerID)) {
            playersWithUnsavedData.remove(playerID);
        }
    }

}
