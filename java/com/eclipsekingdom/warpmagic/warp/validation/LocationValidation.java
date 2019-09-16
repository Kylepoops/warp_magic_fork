package com.eclipsekingdom.warpmagic.warp.validation;

import com.eclipsekingdom.warpmagic.PluginConfig;
import org.bukkit.Location;

public class LocationValidation {

    public static final LocationStatus canWarpPointBePlacedAt(Location warpLocation){
        if(!PluginConfig.getValidWorlds().contains(warpLocation.getWorld().getName())){
            return LocationStatus.INVALID_WORLD;
        }else{
            return LocationStatus.VALID;
        }
    }
}

