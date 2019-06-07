package com.eclipsekingdom.warpmagic.warps.warp;

import com.eclipsekingdom.warpmagic.data.DataType;
import com.eclipsekingdom.warpmagic.data.Manager;
import com.eclipsekingdom.warpmagic.util.MapOperations;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class WarpManager extends Manager<UUID, List<Warp>> {

    /* --- constructors --- */

    private WarpManager() {
        super(new DataType<Location>(null) {
            @Override
            public void writeTo(String path, Location data, FileConfiguration config) {

            }

            @Override
            public Location readFrom(String path, FileConfiguration config) {
                return null;
            }
        },"warps", "/data/warp");
    }

    private static final WarpManager WARP_MANAGER = new WarpManager();

    public static final WarpManager getInstance(){
        return WARP_MANAGER;
    }


    /* --- interface --- */

    @Override
    public void load() {
        for(Player player: Bukkit.getOnlinePlayers()){
            cash(player.getUniqueId());
        }
    }

    public void registerWarp(Player player, Warp warp){
        UUID playerID = player.getUniqueId();
        if(!keyToData.containsKey(playerID)){
            registerNewDataName(playerID, player.getDisplayName());
        }
        MapOperations.addItemToList(keyToData, playerID, warp);
        trackUnsavedData(playerID);
    }

    public void removeWarp(Player player, Warp warp){
        UUID playerID = player.getUniqueId();
        MapOperations.removeItemFromList(keyToData, playerID, warp);
        trackUnsavedData(playerID);
    }

    public List<Warp> getWarps(Player player){
        UUID playerID = player.getUniqueId();
        if(keyToData.containsKey(playerID)){
            return keyToData.get(playerID);
        }else{
            return Collections.emptyList();
        }
    }

    public Warp getWarp(Player player, String warpName){
        UUID playerID = player.getUniqueId();
        if(keyToData.containsKey(playerID)){
            List<Warp> warps = keyToData.get(playerID);
            for(Warp warp: warps){
                if(warp.getName().equalsIgnoreCase(warpName)){
                    return warp;
                }
            }
            return null;
        }else{
            return null;
        }
    }

    public int getUsedWarpCount(Player player){
        UUID playerID = player.getUniqueId();
        if(keyToData.containsKey(playerID)){
            List<Warp> warps = keyToData.get(playerID);
            if(warps != null){
                return warps.size();
            }else{
                return 0;
            }
        }else{
            return 0;
        }
    }


    /* --- implementation --- */

    @Override
    protected boolean stillNeeded(UUID uuid) {
        return false;
    }

    @Override
    protected List<UUID> getRequirements(UUID uuid) {
        return Collections.emptyList();
    }

}