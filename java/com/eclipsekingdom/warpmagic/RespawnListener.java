package com.eclipsekingdom.warpmagic;

import com.eclipsekingdom.warpmagic.global.GlobalManager;
import com.eclipsekingdom.warpmagic.warps.home.Home;
import com.eclipsekingdom.warpmagic.warps.home.HomeManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnListener implements Listener {
    public RespawnListener(WarpMagic plugin){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onDeath(PlayerRespawnEvent e){
        Home home = homeManager.getHome(e.getPlayer());
        if(home != null){
            e.setRespawnLocation(home.getLocation());
            Bukkit.getConsoleSender().sendMessage(home.getLocation().toString());
        }else{
            Location spawn = globalManager.getSpawn();
            if(spawn != null){
                e.setRespawnLocation(spawn);
            }else{
                Location hub = globalManager.getHub();
                if(hub != null){
                    e.setRespawnLocation(hub);
                }else{
                    e.setRespawnLocation(e.getPlayer().getWorld().getHighestBlockAt(0,0).getLocation());
                }
            }
        }
    }

    private HomeManager homeManager = HomeManager.getInstance();
    private GlobalManager globalManager = GlobalManager.getInstance();

}
