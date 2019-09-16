package com.eclipsekingdom.warpmagic.jinn.head;

import com.eclipsekingdom.warpmagic.jinn.util.Heads;
import org.bukkit.inventory.ItemStack;

public abstract class JinnHead implements IJinnHead {

    private ItemStack neutral;
    private ItemStack hurt;

    public JinnHead(Heads neutral, Heads hurt){
        this.neutral = neutral.getItemStack();
        this.hurt = hurt.getItemStack();
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
