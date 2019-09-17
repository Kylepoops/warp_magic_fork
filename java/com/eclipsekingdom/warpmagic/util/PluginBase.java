package com.eclipsekingdom.warpmagic.util;

import com.eclipsekingdom.warpmagic.warp.Dynmap;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class PluginBase {

    private static String dynmapNameSpace = "dynmap";
    private static String topChatNameSpace = "TopChat";

    private static Dynmap dynmap;
    private static boolean usingDynmap = false;
    private static Plugin topChat;
    private static boolean usingTopChat = false;

    public PluginBase(){
        loadDependencies();
    }

    private void loadDependencies(){
        loadTopChat();
        loadDynmap();
    }

    public static void disableDependencies(){
        if(usingDynmap){
            dynmap.disable();
        }
    }


    private void loadDynmap(){
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(dynmapNameSpace);
        if(plugin != null){
            dynmap = new Dynmap(plugin);
            usingDynmap = true;
            ConsoleSender.sendMessage(dynmapNameSpace + " detected");
        }
    }

    private void loadTopChat(){
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin(topChatNameSpace);
        if(plugin != null){
            topChat = plugin;
            usingTopChat = true;
            ConsoleSender.sendMessage(topChatNameSpace + " detected");
        }
    }

    public static Dynmap getDynmap() {
        return dynmap;
    }

    public static boolean isUsingDynmap() {
        return usingDynmap;
    }

    public static Plugin getTopChat() {
        return topChat;
    }

    public static boolean isUsingTopChat() {
        return usingTopChat;
    }
}
