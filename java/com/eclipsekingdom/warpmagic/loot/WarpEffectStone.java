package com.eclipsekingdom.warpmagic.loot;

import com.eclipsekingdom.warpmagic.Permissions;
import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.data.UserCache;
import com.eclipsekingdom.warpmagic.data.UserData;
import com.eclipsekingdom.warpmagic.util.language.Message;
import com.eclipsekingdom.warpmagic.warp.effect.Effect;
import com.eclipsekingdom.warpmagic.warp.effect.EffectType;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class WarpEffectStone implements ILoot {

    private static String uniqueLore = WarpMagic.themeLight + "Where did you learn how to teleport?";

    public static ItemStack build(EffectType effectType) {
        ItemStack effectStone = new ItemStack(Material.EMERALD);
        ItemMeta meta = effectStone.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_GREEN + "Effect Stone");
        ArrayList<String> loreLines = new ArrayList<>();
        loreLines.add(uniqueLore);
        loreLines.add(ChatColor.RED + "1 use only - click to activate");
        loreLines.add(ChatColor.GRAY + "+1 warp effect: " + effectType.getEffect().getName());
        meta.setLore(loreLines);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        effectStone.setItemMeta(meta);
        return effectStone;
    }

    @Override
    public ItemStack buildGeneric() {
        return build(EffectType.NONE);
    }

    @Override
    public String getUniqueLore() {
        return uniqueLore;
    }

    @Override
    public void use(Player player, ItemStack itemStack) {
        UserData userData = UserCache.getData(player);
        String effectString = itemStack.getItemMeta().getLore().get(2).split("effect: ")[1];
        EffectType effectType = EffectType.fromItemName(effectString);
        Effect effect = effectType.getEffect();
        if (!userData.getEffects().contains(effectType) && !Permissions.hasAllEffects(player)) {
            userData.addEffect(effectType);
            player.sendMessage(Message.SUCCESS_EFFECT_UNLOCK.getFromEffect(effect.getName()));
            player.sendMessage(Message.SUGGEST_WE.get());
            itemStack.setAmount(itemStack.getAmount() - 1);
            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_TWINKLE_FAR, (float) 1, (float) 2);
        } else {
            player.sendMessage(Message.ERROR_EFFECT_UNLOCKED.getFromEffect(effect.getName()));
        }
    }

}
