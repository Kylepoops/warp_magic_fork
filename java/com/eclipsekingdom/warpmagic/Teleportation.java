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

    private final EffectManager effectManager = EffectManager.getInstance();
    private final WarpMagic plugin;

    public Teleportation(){
        this.plugin = WarpMagic.plugin;
    }

    public void sendTo(Player player, Location location){
        TeleportValidation.Status status = TeleportValidation.getStatus(player, location);
        if(status == TeleportValidation.Status.VALID){
            Effect effect = effectManager.getCurrent(player);
            if(effect != null){
                playSound(player.getLocation());
                effect.run(player, plugin);
                player.teleport(location);

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        effect.run(player, plugin);
                        playSound(player.getLocation());
                    }
                }, 1);
            }else{
                playSound(player.getLocation());
                player.teleport(location);
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        playSound(player.getLocation());
                    }
                }, 1);
            }
        }else{
            if(player.isOnline()){
                Notifications.sendWarning(player, status.message);
            }
        }
    }

    public static void playSound(Location location){
        location.getWorld().playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 1.3f);
    }

    public static Location makeSafe(Location location){
        while(location.clone().add(0,-1,0).getBlock().isPassable()){
            location.add(0,-1,0);
        }
        while(!location.getBlock().isPassable() || !location.clone().add(0,1,0).getBlock().isPassable()){
            location.add(0,1,0);
        }
        return location;
    }

    public static Location makeNotUnderground(Location location){
        while(!location.getBlock().isPassable() || !location.clone().add(0,1,0).getBlock().isPassable()){
            location.add(0,1,0);
        }
        return location;
    }

}
