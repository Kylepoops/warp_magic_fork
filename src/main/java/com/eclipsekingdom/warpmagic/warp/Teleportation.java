package com.eclipsekingdom.warpmagic.warp;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.data.UserCache;
import com.eclipsekingdom.warpmagic.util.Cooldown;
import com.eclipsekingdom.warpmagic.util.PermInfo;
import com.eclipsekingdom.warpmagic.warp.effect.Effect;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTeleportEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.UUID;

import static com.eclipsekingdom.warpmagic.sys.lang.Message.*;

public class Teleportation implements Listener {

    public Teleportation() {
        Plugin plugin = WarpMagic.getPlugin();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    private static HashMap<UUID, Cooldown> playerToCooldown = new HashMap<>();
    private static HashMap<UUID, UUID> primedPlayers = new HashMap<>();

    public static void sendTo(Player player, Location location) {
        UUID playerID = player.getUniqueId();
        if (!playerToCooldown.containsKey(playerID)) {
            if (location != null && location.getWorld() != null) {
                PermInfo permInfo = UserCache.getPerms(playerID);
                if (permInfo.getChargeUp() > 0) {
                    if (primedPlayers.containsKey(playerID)) {
                        primedPlayers.remove(playerID);
                        player.sendMessage(ChatColor.RED + MISC_TP_CANCELLED.toString());
                    }
                    player.sendMessage(ChatColor.GREEN + MISC_TELEPORTING.toString());
                    player.sendMessage(ChatColor.DARK_GREEN + MISC_TP_FOCUS.fromSeconds(String.valueOf(permInfo.getChargeUp())));
                    UUID tID = UUID.randomUUID();
                    primedPlayers.put(playerID, tID);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(WarpMagic.getPlugin(), () -> {
                        if (primedPlayers.containsKey(playerID) && tID.equals(primedPlayers.get(playerID))) {
                            primedPlayers.remove(playerID);
                            startCooldown(permInfo, playerID);
                            Effect.play(player);
                            player.teleport(location);
                            Bukkit.getScheduler().scheduleSyncDelayedTask(WarpMagic.getPlugin(), ()->{
                                Effect.play(player);},1);
                        }
                    }, permInfo.getChargeUp() * 20);
                } else {
                    startCooldown(permInfo, playerID);
                    Effect.play(player);
                    player.teleport(location);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(WarpMagic.getPlugin(), ()->{
                        Effect.play(player);},1);
                }
            } else {
                player.sendMessage(ChatColor.RED + WARN_INVALID_LOCATION.toString());
            }
        } else {
            Cooldown cooldown = playerToCooldown.get(playerID);
            player.sendMessage(ChatColor.RED + WARN_TELEPORT_COOLDOWN.fromSeconds(String.valueOf(cooldown.getCount())));
        }
    }

    private static void startCooldown(PermInfo permInfo, UUID playerID) {
        if (permInfo.getCooldown() > 0) {
            playerToCooldown.put(playerID, new Cooldown(permInfo.getCooldown(), () -> {
                playerToCooldown.remove(playerID);
            }));
        }
    }

    public static void sendToRequest(Player player, Location location) {
        Effect.play(player);
        player.teleport(location);
        Bukkit.getScheduler().scheduleSyncDelayedTask(WarpMagic.getPlugin(), ()->{
            Effect.play(player);},1);
    }

    @EventHandler
    public void onDamaged(EntityDamageEvent e) {
        Entity entity = e.getEntity();
        UUID entityID = entity.getUniqueId();
        if (primedPlayers.containsKey(entityID)) {
            primedPlayers.remove(entityID);
            entity.sendMessage(ChatColor.RED + MISC_TP_CANCELLED.toString());
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        UUID playerID = player.getUniqueId();
        if (primedPlayers.containsKey(playerID)) {
            primedPlayers.remove(playerID);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        UUID playerID = player.getUniqueId();
        if (primedPlayers.containsKey(playerID)) {
            if(!e.getFrom().getBlock().equals(e.getTo().getBlock())){
                primedPlayers.remove(playerID);
                player.sendMessage(ChatColor.RED + MISC_TP_CANCELLED.toString());
            }
        }
    }

    @EventHandler
    public void onTeleport(EntityTeleportEvent e) {
        Entity entity = e.getEntity();
        UUID entityID = entity.getUniqueId();
        if (primedPlayers.containsKey(entityID)) {
            primedPlayers.remove(entityID);
            entity.sendMessage(ChatColor.RED + MISC_TP_CANCELLED.toString());
        }
    }

}
