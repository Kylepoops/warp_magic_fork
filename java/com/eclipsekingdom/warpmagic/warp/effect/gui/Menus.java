package com.eclipsekingdom.warpmagic.warp.effect.gui;

import com.eclipsekingdom.warpmagic.Permissions;
import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.data.UserCache;
import com.eclipsekingdom.warpmagic.data.UserData;
import com.eclipsekingdom.warpmagic.warp.effect.Effect;
import com.eclipsekingdom.warpmagic.warp.effect.EffectType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Menus {

    private static final int INVENTORY_SIZE = 9 * 2;

    public static Inventory buildPlayerEffectMenu(Player player){
        Inventory inventory = Bukkit.createInventory(null, INVENTORY_SIZE, WarpMagic.themeDark+""+ChatColor.BOLD + "Warp Effects");

        UserData userData = UserCache.getData(player);

        Effect currentEffect = userData.hasCurrentEffect()? userData.getCurrentEffect().getEffect() : EffectType.NONE.getEffect();
        List<Effect> effects = new ArrayList<>();
        for(EffectType effectType: userData.getEffects()){
            effects.add(effectType.getEffect());
        }

        if(Permissions.hasAllEffects(player)){
            List<Effect> allEffects = new ArrayList<>();
            for(EffectType effectType: EffectType.values()){
                if(effectType != effectType.UNKNOWN && effectType != effectType.NONE){
                    allEffects.add(effectType.getEffect());
                }
            }
            effects = allEffects;
        }

        addHeader(inventory, warpBorder, createIconWithLore(currentEffect.getMaterial(), ChatColor.GRAY + " - Active Effect - ", currentEffect.getName()));
        inventory.setItem(9,getMenuItem(EffectType.NONE.getEffect()));

        final int offset = 10;
        int index = 0;
        while (index < effects.size()){
            inventory.setItem(index+offset, getMenuItem(effects.get(index)));
            index++;
        }

        return inventory;
    }

    private static ItemStack warpBorder = createIcon(Material.LIME_STAINED_GLASS_PANE, ChatColor.DARK_GREEN +  "•");

    private static void addHeader(Inventory inventory, ItemStack background, ItemStack centralItem){
        inventory.setItem(0, background);
        inventory.setItem(1,background);
        inventory.setItem(2,background);
        inventory.setItem(3,background);
        inventory.setItem(4,centralItem);
        inventory.setItem(5,background);
        inventory.setItem(6,background);
        inventory.setItem(7,background);
        inventory.setItem(8,background);
    }

    private static ItemStack getMenuItem(Effect effect) {
        ItemStack menuItem = new ItemStack(effect.getMaterial());
        ItemMeta meta = menuItem.getItemMeta();
        meta.setDisplayName(effect.getName());
        meta.setLore(Collections.singletonList(effect.getColor() + "•○•○•○•"));
        menuItem.setItemMeta(meta);
        return menuItem;
    }

    private static ItemStack createIcon(Material material, String name){
        ItemStack icon = new ItemStack(material, 1);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(name);
        icon.setItemMeta(meta);
        return icon;
    }

    private static ItemStack createIconWithLore(Material material, String name, String firstLoreLine){
        ItemStack icon = new ItemStack(material, 1);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName(name);
        LinkedList<String> lore = new LinkedList<>();
        lore.add(firstLoreLine);
        meta.setLore(lore);
        icon.setItemMeta(meta);
        return icon;
    }

}
