package com.eclipsekingdom.warpmagic.loot;

import com.eclipsekingdom.warpmagic.data.UserCache;
import com.eclipsekingdom.warpmagic.data.UserData;
import com.eclipsekingdom.warpmagic.sys.config.PluginConfig;
import com.eclipsekingdom.warpmagic.sys.lang.Message;
import com.eclipsekingdom.warpmagic.util.LootUtil;
import com.eclipsekingdom.warpmagic.util.PermInfo;
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

    private static String uniqueLore = ChatColor.GREEN + Message.LORE_VORTEXSTONE.toString();

    @Override
    public ItemStack buildGeneric() {
        ItemStack vortexStone = new ItemStack(Material.EMERALD);
        ItemMeta meta = vortexStone.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_GREEN + Message.ITEM_VORTEXSTONE.toString());
        ArrayList<String> loreLines = new ArrayList<>();
        loreLines.add(uniqueLore);
        loreLines.add(ChatColor.RED + Message.MISC_ONE_USE.toString());
        loreLines.add(ChatColor.GRAY + "+1 vortex");
        meta.setLore(loreLines);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
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
        PermInfo permInfo = UserCache.getPerms(player);
        if ((userData.getUnlockedVortexes() + PluginConfig.getBaseVortexNum() + permInfo.getVortexBonus() < permInfo.getVortexMax())) {
            userData.incrementUnlockedVortexes();
            player.sendMessage(ChatColor.GREEN + "+1 vortex");
            LootUtil.consumeItem(player, itemStack);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_TWINKLE_FAR, 1f, 2f);
        } else {
            player.sendMessage(ChatColor.RED + Message.WARN_MAX_VORTEXES.toString());
        }
    }

    @Override
    public String getNamespace() {
        return "vortexstone";
    }
}
