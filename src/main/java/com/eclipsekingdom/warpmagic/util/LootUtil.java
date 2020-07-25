package com.eclipsekingdom.warpmagic.util;

import com.eclipsekingdom.warpmagic.sys.Version;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class LootUtil {

    @Deprecated
    public static void consumeItem(Player player, ItemStack itemStack) {
        if (Version.current.isNormalItemConsume()) {
            itemStack.setAmount(itemStack.getAmount() - 1);
        } else {
            if (Version.current.hasOffhand()) {
                ItemStack toSet = itemStack.getAmount() > 1 ? new ItemStack(itemStack.getType(), itemStack.getAmount() - 1) : null;
                if (toSet != null) {
                    toSet.setItemMeta(itemStack.getItemMeta());
                }
                PlayerInventory playerInventory = player.getInventory();
                if (itemStack.equals(playerInventory.getItemInMainHand())) {
                    playerInventory.setItemInMainHand(toSet);
                } else if (itemStack.equals(playerInventory.getItemInOffHand())) {
                    playerInventory.setItemInOffHand(toSet);
                }
            } else {
                ItemStack toSet = itemStack.getAmount() > 1 ? new ItemStack(itemStack.getType(), itemStack.getAmount() - 1) : null;
                if (toSet != null) {
                    toSet.setItemMeta(itemStack.getItemMeta());
                }
                player.setItemInHand(toSet);
            }
        }
    }

}
