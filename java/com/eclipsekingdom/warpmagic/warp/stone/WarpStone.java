package com.eclipsekingdom.warpmagic.warp.stone;

import com.eclipsekingdom.warpmagic.util.ItemOperations;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class WarpStone {


    public WarpStone(){
        this.itemStack = buildItemStack();
    }

    public ItemStack asItem(){
        return itemStack.clone();
    }

    public static boolean isWarpStone(ItemStack itemStack){
        if(ItemOperations.hasLoreID(itemStack)){
            return LORE_ID.equals(ItemOperations.getLoreID(itemStack));
        }else{
            return false;
        }
    }

    private final ItemStack itemStack;


    private static final String LORE_ID = ChatColor.GREEN + "it is only partly grounded in this dimension";

    private ItemStack buildItemStack(){
        ItemStack warpStone = new ItemStack(Material.EMERALD);
        ItemMeta meta = warpStone.getItemMeta();

        meta.setDisplayName(ChatColor.DARK_GREEN + "Warp Stone");
        ArrayList<String> loreLines = new ArrayList<>();
        loreLines.add(LORE_ID);
        loreLines.add(ChatColor.RED + "1 use only - click to activate");
        loreLines.add(ChatColor.GRAY + "+1 warp");
        meta.setLore(loreLines);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.DURABILITY,1,true);
        warpStone.setItemMeta(meta);
        return warpStone;
    }



}
