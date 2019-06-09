package com.eclipsekingdom.warpmagic.util.loot;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.effect.EffectType;
import com.eclipsekingdom.warpmagic.effect.WarpEffectStone;
import com.eclipsekingdom.warpmagic.warps.vortex.VortexStone;
import com.eclipsekingdom.warpmagic.warps.warp.WarpStone;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

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
        loots.add(VortexStone.getInstance());
        loots.add(WarpEffectStone.getInstance());
        return loots;
    }

    private final List<Loot> craftProtectList = buildLootList();
    private final List<Loot> buildCraftPortectList(){
        List<Loot> loots = new ArrayList<>();
        return loots;
    }



    @EventHandler
    public void onCraft(CraftItemEvent e){
        for(ItemStack itemStack: e.getInventory()){
            for(Loot loot: craftProtectList){
                if(loot.isInstance(itemStack)){
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent e){
        for(ItemStack itemStack: e.getInventory()){
            for(Loot loot: craftProtectList){
                if(loot.isInstance(itemStack)){
                    e.getInventory().setItem(0, new ItemStack(Material.AIR,1));
                }
            }
        }
    }

}
