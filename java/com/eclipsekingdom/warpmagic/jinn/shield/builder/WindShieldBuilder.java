package com.eclipsekingdom.warpmagic.jinn.shield.builder;

import com.eclipsekingdom.warpmagic.jinn.shield.IShieldBuilder;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.block.BlockState;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class WindShieldBuilder implements IShieldBuilder {

    @Override
    public ItemStack craftShield() {
        ItemStack shield = new ItemStack(Material.SHIELD);
        BlockStateMeta shieldMeta = (BlockStateMeta) shield.getItemMeta();
        BlockState state = shieldMeta.getBlockState();
        Banner bannerState = (Banner) state;
        bannerState.setBaseColor(DyeColor.LIME);
        bannerState.addPattern(new Pattern(DyeColor.GREEN, PatternType.FLOWER));
        bannerState.addPattern(new Pattern(DyeColor.LIME, PatternType.STRAIGHT_CROSS));
        bannerState.addPattern(new Pattern(DyeColor.LIME, PatternType.BORDER));
        shieldMeta.setBlockState(bannerState);
        shield.setItemMeta(shieldMeta);
        return shield;
    }

}
