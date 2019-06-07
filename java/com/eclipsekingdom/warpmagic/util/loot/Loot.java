package com.eclipsekingdom.warpmagic.util.loot;

import com.eclipsekingdom.warpmagic.util.operations.ItemOperations;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Loot {

    public ItemStack asItem(){
        return itemStack.clone();
    }

    public boolean isInstance(ItemStack itemStack){
        if(ItemOperations.hasLoreID(itemStack)){
            return uniqueLore.equals(ItemOperations.getLoreID(itemStack));
        }else{
            return false;
        }
    }

    public abstract void use(Player player, ItemStack itemStack);

    protected abstract String initUniqueLore();
    protected final String uniqueLore = initUniqueLore();

    protected abstract ItemStack buildItemStack(String uniqueLore);
    private final ItemStack itemStack = buildItemStack(uniqueLore);



}
