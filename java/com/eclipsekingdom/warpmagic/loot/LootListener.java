package com.eclipsekingdom.warpmagic.loot;

import com.eclipsekingdom.warpmagic.WarpMagic;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class LootListener implements Listener {

    public LootListener(WarpMagic plugin){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e){
        if(e.hasItem() && isRightClick(e.getAction())){
            ItemStack itemStack = e.getItem();
            ILoot loot = LootType.getLoot(itemStack);
            if(loot != null){
                loot.use(e.getPlayer(), itemStack);
            }
        }
    }

    private boolean isRightClick(Action action){
        return action == Action.RIGHT_CLICK_BLOCK || action == Action.RIGHT_CLICK_AIR;
    }

    @EventHandler
    public void onCraft(CraftItemEvent e){
        for(ItemStack itemStack: e.getInventory()){
            if(LootType.isLoot(itemStack)){
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onPrepareCraft(PrepareItemCraftEvent e){
        for(ItemStack itemStack: e.getInventory()){
            if(LootType.isLoot(itemStack)){
                e.getInventory().setItem(0, new ItemStack(Material.AIR,1));
                return;
            }
        }
    }

}
