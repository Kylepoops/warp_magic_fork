package com.eclipsekingdom.warpmagic.loot;


import org.bukkit.inventory.ItemStack;

public enum LootType {
    WARP_STONE(new WarpStone()),
    VORTEX_STONE(new VortexStone()),
    ;

    private ILoot loot;

    LootType(ILoot loot) {
        this.loot = loot;
    }

    public ILoot getLoot() {
        return loot;
    }

    public static ILoot getLoot(ItemStack itemStack) {
        if (itemStack != null && itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore()) {
            return LootType.getLoot(itemStack.getItemMeta().getLore().get(0));
        } else {
            return null;
        }
    }

    private static ILoot getLoot(String firstLoreLine) {
        for (LootType lootType : LootType.values()) {
            if (lootType.loot.getUniqueLore().equals(firstLoreLine)) {
                return lootType.loot;
            }
        }
        return null;
    }

    public static boolean isLoot(ItemStack itemStack) {
        return getLoot(itemStack) != null;
    }


}
