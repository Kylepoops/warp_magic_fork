package com.eclipsekingdom.warpmagic.util;

import org.bukkit.inventory.ItemStack;

public class ItemOperations {

    public static void consumeItem(ItemStack itemStack){
        itemStack.setAmount(itemStack.getAmount() - 1);
    }

    public static boolean hasLoreID(ItemStack itemStack){
        return (itemStack != null
                && itemStack.hasItemMeta()
                && itemStack.getItemMeta().hasLore()
                && itemStack.getItemMeta().getLore().size() > 0
                && itemStack.getItemMeta().getLore().get(0) != null
        );
    }

    public static String getLoreID(ItemStack itemStack){
        return itemStack.getItemMeta().getLore().get(0);
    }

}
