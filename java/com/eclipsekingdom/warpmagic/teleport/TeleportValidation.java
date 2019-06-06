package com.eclipsekingdom.warpmagic.teleport;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportValidation {

    private TeleportValidation(){}

    public static final TeleportValidation getInstance(){
        return TELEPORT_VALIDATION_INSTANCE;
    }


    public TeleportStatus getStatus(Player player, Location location){
        if(player.isOnline()){
            if(location != null){
                return TeleportStatus.VALID;
            }else{
                return TeleportStatus.LOCATION_NOT_FOUND;
            }
        }else{
            return TeleportStatus.PLAYER_OFFLINE;
        }
    }




    private static final TeleportValidation TELEPORT_VALIDATION_INSTANCE = new TeleportValidation();



}
