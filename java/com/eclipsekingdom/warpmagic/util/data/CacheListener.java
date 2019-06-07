package com.eclipsekingdom.warpmagic.util.data;

import com.eclipsekingdom.warpmagic.WarpMagic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CacheListener implements Listener {

    public CacheListener(WarpMagic plugin){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler (ignoreCancelled = true)
    public void onJoin(PlayerJoinEvent e){
        pluginData.cache(e.getPlayer());
    }

    @EventHandler (ignoreCancelled = true)
    public void onQuit(PlayerQuitEvent e){
        pluginData.forget(e.getPlayer());
    }

    private PluginData pluginData = PluginData.getInstance();

}