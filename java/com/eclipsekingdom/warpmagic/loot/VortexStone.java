package com.eclipsekingdom.warpmagic.loot;

import com.eclipsekingdom.warpmagic.Permissions;
import com.eclipsekingdom.warpmagic.PluginConfig;
import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.data.UserCache;
import com.eclipsekingdom.warpmagic.data.UserData;
import com.eclipsekingdom.warpmagic.util.language.Message;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class VortexStone implements ILoot {

    private static String uniqueLore = WarpMagic.themeLight + "Only partly grounded in this dimension";

    @Override
    public ItemStack buildGeneric() {
        ItemStack vortexStone = new ItemStack(Material.EMERALD);
        ItemMeta meta = vortexStone.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_GREEN + "Vortex Stone");
        ArrayList<String> loreLines = new ArrayList<>();
        loreLines.add(uniqueLore);
        loreLines.add(ChatColor.RED + "1 use only - click to activate");
        loreLines.add(ChatColor.GRAY + "+1 vortex");
        meta.setLore(loreLines);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.DURABILITY,1,true);
        vortexStone.setItemMeta(meta);
        return vortexStone;
    }

    @Override
    public String getUniqueLore() {
        return uniqueLore;
    }

    @Override
    public void use(Player player, ItemStack itemStack) {
        UserData userData = UserCache.getData(player);
        if( (userData.getTotalVortexNumber(player) < PluginConfig.getMaxVortexNum()) || Permissions.hasNoVortexLimit(player) ){
            userData.incrementUnlockedVortexes();
            player.sendMessage(WarpMagic.themeLight + "+1 vortex");
            itemStack.setAmount(itemStack.getAmount() - 1);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_TWINKLE_FAR, (float)1 ,(float)2);
        }else{
            player.sendMessage(Message.ERROR_MAX_VORTEXES.get());
        }
    }
}
