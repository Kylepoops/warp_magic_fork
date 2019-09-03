package com.eclipsekingdom.warpmagic.jinn.shield;

import org.bukkit.inventory.ItemStack;

public abstract class JinnShield implements IJinnShield {

    private ItemStack neutral;
    private ItemStack hurt;

    public JinnShield(IShieldBuilder neutral, IShieldBuilder hurt){
        this.neutral = neutral.craftShield();
        this.hurt = hurt.craftShield();
    }

    @Override
    public ItemStack getNeutral() {
        return neutral;
    }

    @Override
    public ItemStack getHurt() {
        return hurt;
    }

}
