package com.eclipsekingdom.warpmagic;

import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.effect.EffectManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Teleportation {

    public static void sendTo(Player player, Location location){
        TeleportValidation.Status status = TeleportValidation.getStatus(player, location);
        if(status == TeleportValidation.Status.VALID){
            effectManager.playEffect(player);
            player.teleport(location);
            effectManager.playEffect(player);
        }else{
            if(player.isOnline()){
                Notifications.sendWarning(player, status.message);
            }
        }
    }

    private static final EffectManager effectManager = EffectManager.getInstance();

}
