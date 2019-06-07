package com.eclipsekingdom.warpmagic.warps.warp;

import com.eclipsekingdom.warpmagic.Permissions;
import com.eclipsekingdom.warpmagic.PluginConfig;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.util.loot.Loot;
import com.eclipsekingdom.warpmagic.util.operations.ItemOperations;
import com.eclipsekingdom.warpmagic.warps.warp.WarpNumManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class WarpStone extends Loot {


    private WarpStone() {
        super(ChatColor.GREEN + "it is only partly grounded in this dimension");
    }

    private static final WarpStone WARP_STONE = new WarpStone();

    public static final WarpStone getInstance(){
        return WARP_STONE;
    }

    @Override
    public void use(Player player, ItemStack itemStack) {
        if( (warpNumManager.getUnlockedWarpNum(player) < pluginConfig.getMaxWarpNum())
                || Permissions.hasNoWarpLimit(player) ){
            warpNumManager.incrementWarpCapacity(player);
            Notifications.sendSuccess(player, "+1 warp");
            ItemOperations.consumeItem(itemStack);
        }else{
            Notifications.sendWarning(player, "You already have the maximum number of warps");
        }
    }

    @Override
    protected ItemStack buildItemStack() {
        ItemStack warpStone = new ItemStack(Material.EMERALD);
        ItemMeta meta = warpStone.getItemMeta();

        meta.setDisplayName(ChatColor.DARK_GREEN + "Warp Stone");
        ArrayList<String> loreLines = new ArrayList<>();
        loreLines.add(uniqueLore);
        loreLines.add(ChatColor.RED + "1 use only - click to activate");
        loreLines.add(ChatColor.GRAY + "+1 warp");
        meta.setLore(loreLines);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.DURABILITY,1,true);
        warpStone.setItemMeta(meta);
        return warpStone;
    }

    private final WarpNumManager warpNumManager = WarpNumManager.getInstance();
    private final PluginConfig pluginConfig = PluginConfig.getInstance();

}
