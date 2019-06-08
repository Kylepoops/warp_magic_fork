package com.eclipsekingdom.warpmagic.warps.warp;

import com.eclipsekingdom.warpmagic.util.data.DataType;
import com.eclipsekingdom.warpmagic.util.data.Manager;
import com.eclipsekingdom.warpmagic.util.operations.MapOperations;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.World;

import java.util.*;

public class WarpManager extends Manager<UUID, List<Warp>> {

    /* --- constructors --- */

    private WarpManager() {
        super(new DataType<List<Warp>>(Collections.emptyList()) {
            @Override
            public void writeTo(String path, List<Warp> warps, FileConfiguration config) {
                for(Warp warp: warps){
                    String warpPath = path + "." + warp.getName();
                    Location location = warp.getLocation();
                    config.set(warpPath+".world", location.getWorld().getName());
                    config.set(warpPath+".x", location.getX());
                    config.set(warpPath+".y", location.getY());
                    config.set(warpPath+".z", location.getY());
                    config.set(warpPath+".yaw", location.getYaw());
                    config.set(warpPath+".pitch", location.getPitch());
                }
            }

            @Override
            public List<Warp> readFrom(String path, FileConfiguration config) {
                List<Warp> warps = new ArrayList<>();
                for(String warpName: config.getConfigurationSection(path).getKeys(false)){
                    String warpPath = path + "." + warpName;
                    World world = Bukkit.getWorld(config.getString(warpPath+".world"));
                    double x = config.getDouble(warpPath+".x");
                    double y = config.getDouble(warpPath+".y");
                    double z = config.getDouble(warpPath+".z");
                    float yaw = (float)config.getDouble(warpPath+".yaw");
                    float pitch = (float)config.getDouble(warpPath+".pitch");
                    Location location = new Location(world, x, y, z, yaw, pitch);
                    if(location != null){
                        warps.add(new Warp(warpName, location));
                    }
                }
                return warps;
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
            cache(player.getUniqueId());
        }
    }

    public void registerWarp(Player player, Warp warp){
        UUID playerID = player.getUniqueId();
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
    protected boolean stillNeeded(UUID uuid, UUID leavingUUID) {
        return false;
    }

    @Override
    protected List<UUID> getRequirements(UUID uuid) {
        return Collections.emptyList();
    }

}
