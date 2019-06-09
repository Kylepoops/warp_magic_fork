package com.eclipsekingdom.warpmagic.util.loot;

import com.eclipsekingdom.warpmagic.util.operations.ItemOperations;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Loot {

    public Loot(String uniqueLore){
        this.uniqueLore = uniqueLore;
    }

    public ItemStack asItem(){
        return buildItemStack();
    }

    public boolean isInstance(ItemStack itemStack){
        if(ItemOperations.hasLoreID(itemStack)){
            return uniqueLore.equals(ItemOperations.getLoreID(itemStack));
        }else{
            return false;
        }
    }

    public abstract void use(Player player, ItemStack itemStack);

    protected final String uniqueLore;

    protected abstract ItemStack buildItemStack();



}
