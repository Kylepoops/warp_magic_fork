package com.eclipsekingdom.warpmagic.warps;

import com.eclipsekingdom.warpmagic.PluginConfig;
import org.bukkit.Location;

public class LocationValidation {

    public enum Status {

        VALID("success"),
        INVALID_WORLD("Warp points can not be set in this world");

        Status(String message){
            this.message = message;
        }

        public final String message;

    }

    public static final Status canWarpPointBePlacedAt(Location warpLocation){
        if(!pluginConfig.getValidWorlds().contains(warpLocation.getWorld().getName())){
            return Status.INVALID_WORLD;
        }else{
            return Status.VALID;
        }
    }

    private static final PluginConfig pluginConfig = PluginConfig.getInstance();

}

