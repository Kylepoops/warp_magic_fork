package com.eclipsekingdom.warpmagic.effect.gui;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.effect.Effect;
import com.eclipsekingdom.warpmagic.effect.EffectManager;
import com.eclipsekingdom.warpmagic.effect.EffectType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class InputListener implements Listener {

    public InputListener(WarpMagic plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent e) {
        if(hasSession(e.getWhoClicked())){
            Player player = (Player) e.getWhoClicked();
            e.setCancelled(true);
            if (isMenuClick(e)) {
                ItemStack effectMenuItem = e.getClickedInventory().getItem(e.getSlot());
                if(isEffectCandidate(effectMenuItem)){
                    EffectType effectType = EffectType.fromString(effectMenuItem.getItemMeta().getDisplayName().substring(2));
                    if(effectType != EffectType.UNKNOWN){
                        playSound(player);
                        Effect effect = effectType.getEffect();
                        effectManager.setCurrent(player, effect);
                        updateHeaderSlot(e.getClickedInventory(), effect);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if(e.getPlayer() instanceof Player) {
            if (liveSessions.hasSession((Player) e.getPlayer())) {
                liveSessions.end((Player) e.getPlayer());
            }
        }
    }



    private final EffectManager effectManager = EffectManager.getInstance();
    private final LiveSessions liveSessions = LiveSessions.getInstance();

    private static boolean isMenuClick(InventoryClickEvent e){
        return isTopInventory(e.getClickedInventory(), e.getWhoClicked());
    }
    private static boolean isTopInventory(Inventory inventory, HumanEntity humanEntity){
        return humanEntity.getOpenInventory().getTopInventory().equals(inventory);
    }
    private Boolean isEmpty(ItemStack itemStack){
        return (itemStack == null  || itemStack.getType() == Material.AIR);
    }
    private boolean hasSession(HumanEntity humanEntity){
        if(humanEntity instanceof Player){
            return liveSessions.hasSession((Player) humanEntity);
        }else{
            return false;
        }
    }
    private void updateHeaderSlot(Inventory inventory, Effect effect){
        ItemStack currentItem = inventory.getItem(4);
        currentItem.setType(effect.getMaterial());
        ItemMeta meta = currentItem.getItemMeta();
        meta.setLore(Collections.singletonList(effect.getName()));
        currentItem.setItemMeta(meta);
    }
    private boolean isEffectCandidate(ItemStack itemStack){
        return (!isEmpty(itemStack) && itemStack.hasItemMeta());
    }

    private void playSound(Player player){
        player.playSound(player.getLocation(),Sound.BLOCK_STONE_BUTTON_CLICK_ON, 0.5f,1.2f);
    }

}
