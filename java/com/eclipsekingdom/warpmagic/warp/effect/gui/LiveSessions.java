package com.eclipsekingdom.warpmagic.warp.effect.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class LiveSessions {

    private static Set<UUID> playersWithSession = new HashSet<>();

    public static void disable(){
        for(Player player: Bukkit.getOnlinePlayers()){
            playersWithSession.remove(player.getUniqueId());
            player.closeInventory();
        }
    }

    public static void launch(Player player){
        end(player);
        playersWithSession.add(player.getUniqueId());
        player.openInventory(Menus.buildPlayerEffectMenu(player));
    }

    public static void end(Player player){
        playersWithSession.remove(player.getUniqueId());
    }

    public static boolean hasSession(Player player){
        return playersWithSession.contains(player.getUniqueId());
    }

}
