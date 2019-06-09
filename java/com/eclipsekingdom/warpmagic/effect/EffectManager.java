package com.eclipsekingdom.warpmagic.effect;

import com.eclipsekingdom.warpmagic.util.data.DataType;
import com.eclipsekingdom.warpmagic.util.data.Manager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class EffectManager extends Manager<UUID,EffectInfo> {

    protected EffectManager() {
        super(new DataType<EffectInfo>(new EffectInfo()) {
            @Override
            public void writeTo(String path, EffectInfo data, FileConfiguration config) {
                config.set(path+".current", EffectType.fromEffect(data.getCurrentEffect()).toString());
                List<String> effectsAsStringList = new ArrayList<>();
                for(Effect effect: data.getEffects()){
                    effectsAsStringList.add(EffectType.fromEffect(effect).toString());
                }
                config.set(path+".effects", effectsAsStringList);
            }

            @Override
            public EffectInfo readFrom(String path, FileConfiguration config) {
                Effect currentEffect = EffectType.fromString(config.getString(path+".current")).getEffect();
                List<Effect> effects = new ArrayList<>();
                for(String effectString: config.getStringList(path+".effects")){
                    effects.add(EffectType.fromString(effectString).getEffect());
                }
                return new EffectInfo(currentEffect, effects);
            }

        }, "effects", "/data/effect");
    }

    private static final EffectManager EFFECT_MANAGER = new EffectManager();

    public static final EffectManager getInstance(){
        return EFFECT_MANAGER;
    }

    @Override
    public void load() {
        for(Player player: Bukkit.getOnlinePlayers()){
            cache(player.getUniqueId());
        }
    }

    public void setCurrent(Player player, Effect effect){
        UUID playerID = player.getUniqueId();
        if(keyToData.containsKey(playerID)){
            EffectInfo effectInfo = keyToData.get(playerID);
            effectInfo.setCurrentEffect(effect);
        }
        trackUnsavedData(playerID);
    }

    public void unlockEffect(Player player, Effect effect) {
        UUID playerID = player.getUniqueId();
        if (keyToData.containsKey(playerID)) {
            EffectInfo effectInfo = keyToData.get(playerID);
            effectInfo.unlockEffect(effect);
        }else{
            keyToData.put(playerID, new EffectInfo(EffectType.NONE.getEffect(), Collections.singletonList(effect)));
        }
        trackUnsavedData(playerID);
    }

    public Effect getCurrent(Player player){
        UUID playerID = player.getUniqueId();
        if(keyToData.containsKey(playerID)){
            EffectInfo effectInfo = keyToData.get(playerID);
            return effectInfo.getCurrentEffect();
        }else{
            return null;
        }
    }

    public List<Effect> getEffects(Player player){
        UUID playerID = player.getUniqueId();
        if(keyToData.containsKey(playerID)){
            EffectInfo effectInfo = keyToData.get(playerID);
            return effectInfo.getEffects();
        }else{
            return Collections.emptyList();
        }
    }

    public EffectInfo getEffectInfo(Player player){
        return keyToData.get(player.getUniqueId());
    }



    @Override
    protected boolean stillNeeded(UUID uuid, UUID leavingKey) {
        return false;
    }

    @Override
    protected List<UUID> getRequirements(UUID uuid) {
        return Collections.emptyList();
    }
}
