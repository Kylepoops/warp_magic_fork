package com.eclipsekingdom.warpmagic.warps.validation;

import com.eclipsekingdom.warpmagic.PluginConfig;
import org.bukkit.Location;

public class LocationValidation {


    public static final LocationStatus canWarpPointBePlacedAt(Location warpLocation){
        if(!pluginConfig.getValidWorlds().contains(warpLocation.getWorld().getName())){
            return LocationStatus.INVALID_WORLD;
        }else{
            return LocationStatus.VALID;
        }
    }

    private static final PluginConfig pluginConfig = PluginConfig.getInstance();

}
