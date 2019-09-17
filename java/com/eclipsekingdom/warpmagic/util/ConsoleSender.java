package com.eclipsekingdom.warpmagic.util;

import org.bukkit.Bukkit;

public class ConsoleSender {

    public static void sendMessage(String message) {
        Bukkit.getConsoleSender().sendMessage("[WarpMagic] " + message);
    }

}
