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

public class PluginData {

    private PluginData(){}

    public static final PluginData getInstance(){
        return PLUGIN_DATA_INSTANCE;
    }

    public void load(){
        for(Manager manager: PLAYER_MANAGERS){
            manager.load();
        }
        for(Manager manager: GLOBAL_MANAGERS){
            manager.load();
        }
    }

    public void save(){
        for(Manager manager: PLAYER_MANAGERS){
            manager.save();
        }
        for(Manager manager: GLOBAL_MANAGERS){
            manager.save();
        }
    }

    public void cache(Player player){
        for(Manager manager: PLAYER_MANAGERS){
            manager.cache(player.getUniqueId());
        }
    }

    public void forget(Player player){
        for(Manager manager: PLAYER_MANAGERS){
            manager.forget(player.getUniqueId());
        }
    }

    private static final PluginData PLUGIN_DATA_INSTANCE = new PluginData();
    private static final List<Manager> PLAYER_MANAGERS = buildPlayerManagerList();
    private static final List<Manager> GLOBAL_MANAGERS = buildGlobalManagerList();

    private static List<Manager> buildPlayerManagerList(){
        List<Manager> managers = new ArrayList<>();
        managers.add(WarpNumManager.getInstance());
        managers.add(WarpManager.getInstance());
        managers.add(VortexNumManager.getInstance());
        managers.add(HomeManager.getInstance());
        return managers;
    }

    private static List<Manager> buildGlobalManagerList(){
        List<Manager> managers = new ArrayList<>();
        managers.add(VortexManager.getInstance());
        managers.add(RelationsManager.getInstance());
        return managers;
    }


}
