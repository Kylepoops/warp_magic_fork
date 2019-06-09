package com.eclipsekingdom.warpmagic.warps.vortex;

import com.eclipsekingdom.warpmagic.Permissions;
import com.eclipsekingdom.warpmagic.PluginConfig;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.util.loot.Loot;
import com.eclipsekingdom.warpmagic.util.operations.ItemOperations;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class VortexStone extends Loot {

    private VortexStone() {
        super(ChatColor.GREEN + "Only partly grounded in this dimension");
    }

    private static final VortexStone VORTEX_STONE = new VortexStone();

    public static final VortexStone getInstance(){
        return VORTEX_STONE;
    }

    @Override
    public void use(Player player, ItemStack itemStack) {
        if( (vortexNumManager.getUnlockedVortexNum(player) < pluginConfig.getMaxVortexNum())
                || Permissions.hasNoVortexLimit(player) ){
            vortexNumManager.incrementVortexCapacity(player);
            Notifications.sendSuccess(player, "+1 vortex");
            ItemOperations.consumeItem(itemStack);
        }else{
            Notifications.sendWarning(player, "You already have the maximum number of vortexes");
        }
    }

    @Override
    protected ItemStack buildItemStack() {
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

    private final VortexNumManager vortexNumManager = VortexNumManager.getInstance();
    private final PluginConfig pluginConfig = PluginConfig.getInstance();

}
