package com.eclipsekingdom.warpmagic.data;

import com.eclipsekingdom.warpmagic.Permissions;
import com.eclipsekingdom.warpmagic.PluginConfig;
import com.eclipsekingdom.warpmagic.warp.effect.EffectType;
import com.eclipsekingdom.warpmagic.warp.Friend;
import com.eclipsekingdom.warpmagic.warp.Home;
import com.eclipsekingdom.warpmagic.warp.Warp;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserData {

    private Home home;
    private List<Warp> warps;

    private List<EffectType> effects;
    private EffectType currentEffect;

    private int unlockedWarps;
    private int unlockedVortexes;

    private List<Friend> friends;

    public UserData(){
        this.home = null;
        this.warps = new ArrayList<>();
        this.effects = new ArrayList<>();
        this.unlockedWarps = 0;
        this.unlockedVortexes = 0;
        this.currentEffect = null;
        this.friends = new ArrayList<>();
    }

    public UserData(Home home, List<Warp> warps, List<EffectType> effects, int unlockedWarps, int unlockedVortexes, EffectType currentEffect, List<Friend> friends){
        this.home = home;
        this.warps = warps;
        this.effects = effects;
        this.unlockedWarps = unlockedWarps;
        this.unlockedVortexes = unlockedVortexes;
        this.currentEffect = currentEffect;
        this.friends = friends;
    }

    public boolean hasHome(){
        return home != null;
    }

    public Home getHome(){
        return home;
    }

    public void setHome(Home home){
        this.home = home;
    }

    public List<Warp> getWarps(){
        return warps;
    }

    public void addWarp(Warp warp){
        warps.add(warp);
    }

    public void removeWarp(Warp warp){
        warps.remove(warp);
    }

    public boolean isWarp(String name){
        for(Warp warp: warps){
            if(warp.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    public Warp getWarp(String name){
        for(Warp warp: warps){
            if(warp.getName().equalsIgnoreCase(name)){
                return warp;
            }
        }
        return null;
    }

    public List<EffectType> getEffects(){
        return effects;
    }

    public void addEffect(EffectType effect){
        effects.add(effect);
    }

    public void removeEffect(EffectType effect){
        effects.remove(effect);
    }

    public boolean hasCurrentEffect(){
        return currentEffect != null;
    }

    public void setCurentEffect(EffectType effect){
        currentEffect = effect;
    }

    public EffectType getCurrentEffect(){
        return currentEffect;
    }

    public void incrementUnlockedWarps(){
        unlockedWarps++;
    }

    public int getUnlockedWarps(){
        return unlockedWarps;
    }

    public int getTotalWarpNumber(Player player){
        return unlockedWarps + PluginConfig.getStartingWarpNum() + Permissions.getBonusWarps(player);
    }

    public void incrementUnlockedVortexes(){
        unlockedVortexes++;
    }

    public int getUnlockedVortexes(){
        return unlockedVortexes;
    }

    public int getTotalVortexNumber(Player player){
        return unlockedWarps + PluginConfig.getStartingVortexNum() + Permissions.getBonusVortexes(player);
    }

    public List<Friend> getFriends(){
        return friends;
    }

    public void addFriend(Friend friend){
        friends.add(friend);
    }

    public void removeFriend(UUID friendID){
        for(int i = friends.size() - 1; i>=0; i--){
            if(friends.get(i).getID().equals(friendID)){
                friends.remove(friends.get(i));
            }
        }
    }

    public Friend getFriend(UUID ID){
        for(Friend friend: friends){
            if(friend.getID().equals(ID)){
                return friend;
            }
        }
        return null;
    }

    public Friend getFriend(String name){
        for(Friend friend: friends){
            if(friend.getName().equalsIgnoreCase(name)){
                return friend;
            }
        }
        return null;
    }

    public boolean isEmpty(){
        return
                home == null &&
                warps.size() == 0 &&
                effects.size() == 0 &&
                currentEffect == null &&
                unlockedVortexes == 0 &&
                unlockedWarps == 0
                && friends.size() == 0;
    }

}
