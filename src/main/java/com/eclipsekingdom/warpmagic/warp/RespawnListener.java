package com.eclipsekingdom.warpmagic.warp;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.data.GlobalCache;
import com.eclipsekingdom.warpmagic.data.UserCache;
import com.eclipsekingdom.warpmagic.data.UserData;
import com.eclipsekingdom.warpmagic.warp.global.GlobalPoint;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.Plugin;

public class RespawnListener implements Listener {

    public RespawnListener() {
        Plugin plugin = WarpMagic.getPlugin();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDeath(PlayerRespawnEvent e) {
        UserData userData = UserCache.getData(e.getPlayer());
        if (userData.hasHome()) {
            e.setRespawnLocation(userData.getHome().getLocation());
        } else {
            Location spawn = GlobalCache.get(GlobalPoint.SPAWN);
            if (spawn != null) {
                e.setRespawnLocation(spawn);
            } else {
                Location hub = GlobalCache.get(GlobalPoint.HUB);
                if (hub != null) {
                    e.setRespawnLocation(hub);
                }
            }
        }
    }

    @EventHandler
    public void onNewJoin(PlayerJoinEvent e){
        if(!e.getPlayer().hasPlayedBefore()){
            Location spawn = GlobalCache.get(GlobalPoint.SPAWN);
            if(spawn != null){
                e.getPlayer().teleport(spawn);
            }else{
                Location hub = GlobalCache.get(GlobalPoint.HUB);
                if(hub != null){
                    e.getPlayer().teleport(hub);
                }
            }
        }
    }

}