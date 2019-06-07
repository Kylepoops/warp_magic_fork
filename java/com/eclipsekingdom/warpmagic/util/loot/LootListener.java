package com.eclipsekingdom.warpmagic.util.loot;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.warps.warp.WarpStone;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

public class LootListener implements Listener {

    public LootListener(WarpMagic plugin){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR){
            for(Loot loot: loots){
                if(loot.isInstance(e.getItem())){
                    e.setCancelled(true);
                    loot.use(e.getPlayer(), e.getItem());
                }
            }
        }
    }

    private final List<Loot> loots = buildLootList();
    private final List<Loot> buildLootList(){
        List<Loot> loots = new ArrayList<>();
        loots.add(WarpStone.getInstance());
        return loots;
    }

}
