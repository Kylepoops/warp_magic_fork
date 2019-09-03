package com.eclipsekingdom.warpmagic;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportValidation {

    public enum Status {
        VALID("success"),
        PLAYER_OFFLINE("player offline"),
        LOCATION_NOT_FOUND("The requested location could not be located");

        Status(String message){
            this.message = message;
        }

        public final String message;

    }

    public static Status getStatus(Player player, Location location){
        if(player.isOnline()){
            if(location != null){
                return Status.VALID;
            }else{
                return Status.LOCATION_NOT_FOUND;
            }
        }else{
            return Status.PLAYER_OFFLINE;
        }
    }




}
