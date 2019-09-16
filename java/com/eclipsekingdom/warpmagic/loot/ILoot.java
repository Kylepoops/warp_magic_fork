package com.eclipsekingdom.warpmagic.loot;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface ILoot {

    ItemStack buildGeneric();
    String getUniqueLore();
    void use(Player player, ItemStack itemStack);

}
