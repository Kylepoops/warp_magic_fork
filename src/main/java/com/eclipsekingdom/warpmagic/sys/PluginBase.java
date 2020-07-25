package com.eclipsekingdom.warpmagic.sys;

import com.eclipsekingdom.warpmagic.sys.config.PluginConfig;
import com.eclipsekingdom.warpmagic.sys.lang.Message;
import com.eclipsekingdom.warpmagic.util.Dynmap;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class PluginBase {

    private static String dynmapNameSpace = "dynmap";

    private static Dynmap dynmap;
    private static boolean usingDynmap = false;

    public PluginBase() {
        loadDependencies();
    }

    private void loadDependencies() {
        if (PluginConfig.isUseDynmap()) loadDynmap();
    }

    public static void disableDependencies() {
        if (usingDynmap) {
            dynmap.disable();
        }
    }

    private void loadDynmap() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(dynmapNameSpace);
        if (plugin != null) {
            dynmap = new Dynmap(plugin);
            usingDynmap = true;
            ConsoleSender.sendMessage(Message.CONSOLE_DETECT.fromPlugin(dynmapNameSpace));
        }
    }

    public static Dynmap getDynmap() {
        return dynmap;
    }

    public static boolean isUsingDynmap() {
        return usingDynmap;
    }

}