package com.eclipsekingdom.warpmagic.global;

import com.eclipsekingdom.warpmagic.WarpMagic;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class NewPlayerListener implements Listener {

    public NewPlayerListener(WarpMagic plugin){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onNewJoin(PlayerJoinEvent e){
        if(!e.getPlayer().hasPlayedBefore()){
            Location spawn = globalManager.getSpawn();
            if(spawn != null){
                e.getPlayer().teleport(spawn);
            }else{
                Location hub = globalManager.getHub();
                if(hub != null){
                    e.getPlayer().teleport(hub);
                }
            }
        }
    }


    private GlobalManager globalManager = GlobalManager.getInstance();
}
