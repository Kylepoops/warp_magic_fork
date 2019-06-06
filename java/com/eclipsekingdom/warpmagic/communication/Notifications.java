package com.eclipsekingdom.warpmagic.communication;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Notifications {

    public static void sendWarning(Player player, String message){
        player.sendMessage(ChatColor.RED + message);
    }

    public static void sendTip(Player player, String suggestedCommand, String content){
        player.sendMessage(ChatColor.DARK_GRAY + "Use " + ChatColor.GRAY + "/" + suggestedCommand + ChatColor.DARK_GRAY + " " + content);
    }

    public static void sendFormat(Player player, String format){
        player.sendMessage(ChatColor.RED + "Format is " + ChatColor.GRAY + "/" + format);
    }

    public static void sendNotFound(Player player, String type, String item){
        player.sendMessage(ChatColor.RED + type + " " + themeDark + item + ChatColor.RED + " not found" );
    }

    public static void sendSuccess(Player player, String message){
        player.sendMessage(themeLight + message);
    }

    public static final ChatColor themeDark = ChatColor.DARK_GREEN;
    public static final ChatColor themeLight = ChatColor.GREEN;

}
