package com.eclipsekingdom.warpmagic.util.data;

import com.eclipsekingdom.warpmagic.warps.home.HomeManager;
import com.eclipsekingdom.warpmagic.warps.home.RelationsManager;
import com.eclipsekingdom.warpmagic.warps.vortex.VortexManager;
import com.eclipsekingdom.warpmagic.warps.vortex.VortexNumManager;
import com.eclipsekingdom.warpmagic.warps.warp.WarpManager;
import com.eclipsekingdom.warpmagic.warps.warp.WarpNumManager;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PluginData {

    private PluginData(){}

    public static final PluginData getInstance(){
        return PLUGIN_DATA_INSTANCE;
    }

    public void load(){
        for(Manager manager: MANAGERS){
            manager.load();
        }
    }

    public void save(){
        for(Manager manager: MANAGERS){
            manager.save();
        }
    }

    public void cache(Player player){
        UUID playerID = player.getUniqueId();
        String playerName = player.getDisplayName();
        WarpNumManager.getInstance().cache(playerID);
        WarpManager.getInstance().cache(playerID);
        VortexNumManager.getInstance().cache(playerID);
        HomeManager.getInstance().cache(playerName);
    }

    public void forget(Player player){
        UUID playerID = player.getUniqueId();
        String playerName = player.getDisplayName();
        WarpNumManager.getInstance().forget(playerID);
        WarpManager.getInstance().forget(playerID);
        VortexNumManager.getInstance().forget(playerID);
        HomeManager.getInstance().forget(playerName);
    }

    private static final PluginData PLUGIN_DATA_INSTANCE = new PluginData();
    private static final List<Manager> MANAGERS = buildPlayerManagerList();

    private static List<Manager> buildPlayerManagerList(){
        List<Manager> managers = new ArrayList<>();
        managers.add(WarpNumManager.getInstance());
        managers.add(WarpManager.getInstance());
        managers.add(VortexNumManager.getInstance());
        managers.add(HomeManager.getInstance());
        managers.add(VortexManager.getInstance());
        managers.add(RelationsManager.getInstance());
        return managers;
    }
}
