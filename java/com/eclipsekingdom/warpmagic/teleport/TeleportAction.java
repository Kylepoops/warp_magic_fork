package com.eclipsekingdom.warpmagic.teleport;

import com.eclipsekingdom.warpmagic.communication.Notifications;
import com.eclipsekingdom.warpmagic.effect.EffectManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class TeleportAction {

    public TeleportAction(Player player, Location location){
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


    private TeleportValidation teleportValidation = TeleportValidation.getInstance();
    private EffectManager effectManager = EffectManager.getInstance();

}
