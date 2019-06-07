package com.eclipsekingdom.warpmagic.teleport;

import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.effect.EffectManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Teleportation {

    public static void sendTo(Player player, Location location){
        TeleportStatus status = teleportValidation.getStatus(player, location);
        if(status == TeleportStatus.VALID){
            effectManager.playEffect(player);
            player.teleport(location);
            effectManager.playEffect(player);
        }else{
            if(player.isOnline()){
                Notifications.sendWarning(player, status.getMessage());
            }
        }
    }


    private static final TeleportValidation teleportValidation = TeleportValidation.getInstance();
    private static final EffectManager effectManager = EffectManager.getInstance();

}
