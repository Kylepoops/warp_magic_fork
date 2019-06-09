package com.eclipsekingdom.warpmagic;

import com.eclipsekingdom.warpmagic.effect.Effect;
import com.eclipsekingdom.warpmagic.effect.EffectManager;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.warps.warp.Warp;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Teleportation {

    public Teleportation(WarpMagic plugin){
        this.plugin = plugin;
    }

    public void sendTo(Player player, Location location){
        TeleportValidation.Status status = TeleportValidation.getStatus(player, location);
        if(status == TeleportValidation.Status.VALID){
            Effect effect = effectManager.getCurrent(player);
            if(effect != null){
                playSound(player);
                effect.run(player, plugin);
                player.teleport(location);

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        effect.run(player, plugin);
                        playSound(player);
                    }
                }, 1);
            }else{
                playSound(player);
                player.teleport(location);
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        playSound(player);
                    }
                }, 1);
            }
        }else{
            if(player.isOnline()){
                Notifications.sendWarning(player, status.message);
            }
        }
    }


    private final EffectManager effectManager = EffectManager.getInstance();
    private final WarpMagic plugin;

    private void playSound(Player player){
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 1.3f);
    }

}
