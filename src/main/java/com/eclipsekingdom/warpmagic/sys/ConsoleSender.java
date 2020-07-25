package com.eclipsekingdom.warpmagic.sys;

import org.bukkit.Bukkit;

public class ConsoleSender {

    public static void sendMessage(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage("[Warp Magic] " + message);
    }

}
