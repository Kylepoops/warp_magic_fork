package com.eclipsekingdom.warpmagic.jinn.cloak;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public abstract class JinnCloak implements IJinnCloak {

    private static ItemStack buildCloak(Color color){
        ItemStack scales = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta = (LeatherArmorMeta) scales.getItemMeta();
        meta.setColor(color);
        scales.setItemMeta(meta);
        return scales;
    }

    private static ItemStack buildTail(Color color){
        ItemStack scales = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta meta = (LeatherArmorMeta) scales.getItemMeta();
        meta.setColor(color);
        scales.setItemMeta(meta);
        return scales;
    }

    private ItemStack neutral;
    private ItemStack hurt;

    private ItemStack neutralTail;
    private ItemStack hurtTail;

    public JinnCloak(Color neutralColor, Color hurtColor){
        this.neutral = buildCloak(neutralColor);
        this.hurt = buildCloak(hurtColor);
        this.neutralTail = buildTail(neutralColor);
        this.hurtTail = buildTail(hurtColor);
    }

    @Override
    public ItemStack getNeutral() {
        return neutral;
    }

    @Override
    public ItemStack getHurt() {
        return hurt;
    }


    @Override
    public ItemStack getNeutralTail() {
        return neutralTail;
    }

    @Override
    public ItemStack getHurtTail() {
        return hurtTail;
    }

}


