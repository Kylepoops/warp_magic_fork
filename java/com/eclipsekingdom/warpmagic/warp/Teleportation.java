package com.eclipsekingdom.warpmagic.warp;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.data.UserCache;
import com.eclipsekingdom.warpmagic.data.UserData;
import com.eclipsekingdom.warpmagic.warp.effect.Effect;
import com.eclipsekingdom.warpmagic.warp.validation.TeleportStatus;
import com.eclipsekingdom.warpmagic.warp.validation.TeleportValidation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Teleportation {

    public static void sendTo(Player player, Location location) {
        TeleportStatus status = TeleportValidation.getStatus(player, location);
        if (status == TeleportStatus.VALID) {
            UserData userData = UserCache.getData(player);
            if (userData.hasCurrentEffect()) {
                Effect effect = userData.getCurrentEffect().getEffect();
                playSound(player.getLocation());
                effect.run(player);
                player.teleport(location);
                Bukkit.getScheduler().scheduleSyncDelayedTask(WarpMagic.plugin, new Runnable() {
                    @Override
                    public void run() {
                        effect.run(player);
                        playSound(player.getLocation());
                    }
                }, 1);
            } else {
                playSound(player.getLocation());
                player.teleport(location);
                Bukkit.getScheduler().scheduleSyncDelayedTask(WarpMagic.plugin, new Runnable() {
                    @Override
                    public void run() {
                        playSound(player.getLocation());
                    }
                }, 1);
            }
        } else {
            player.sendMessage(status.message);
        }
    }

    public static void playSound(Location location) {
        location.getWorld().playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 1.3f);
    }

    public static Location makeSafe(Location location) {
        while (location.clone().add(0, -1, 0).getBlock().isPassable()) {
            location.add(0, -1, 0);
        }
        while (!location.getBlock().isPassable() || !location.clone().add(0, 1, 0).getBlock().isPassable()) {
            location.add(0, 1, 0);
        }
        return location;
    }

    public static Location makeNotUnderground(Location location) {
        while (!location.getBlock().isPassable() || !location.clone().add(0, 1, 0).getBlock().isPassable()) {
            location.add(0, 1, 0);
        }
        return location;
    }

}
