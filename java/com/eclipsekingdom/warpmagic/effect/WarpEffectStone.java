package com.eclipsekingdom.warpmagic.effect;

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

public class WarpEffectStone extends Loot {

    public WarpEffectStone(Effect effect){
        super(ChatColor.GREEN + "Where did you learn how to teleport?");
        this.effect = effect;
    }

    private static final WarpEffectStone WARP_EFFECT_STONE = new WarpEffectStone(EffectType.NONE.getEffect());

    public static final WarpEffectStone getInstance(){
        return WARP_EFFECT_STONE;
    }


    @Override
    public void use(Player player, ItemStack itemStack) {
        String effectString = itemStack.getItemMeta().getLore().get(2).split("effect: ")[1].substring(2); //substring 2 gets rid of the chat color strings
        EffectType effectType = EffectType.fromString(effectString);
        Effect effect = effectType.getEffect();
        if(!effectManager.getEffects(player).contains(effect)){
            effectManager.unlockEffect(player, effect);
            Notifications.sendSuccess(player, "You unlocked " + effect.getName());
            ItemOperations.consumeItem(itemStack);
        }else{
            Notifications.sendWarning(player, "You have already unlocked " + effect.getName());
        }
    }

    private EffectManager effectManager = EffectManager.getInstance();


    @Override
    protected ItemStack buildItemStack() {
        ItemStack effectStone = new ItemStack(Material.EMERALD);
        ItemMeta meta = effectStone.getItemMeta();

        meta.setDisplayName(ChatColor.DARK_GREEN + "Warp Effect Stone");
        ArrayList<String> loreLines = new ArrayList<>();
        loreLines.add(uniqueLore);
        loreLines.add(ChatColor.RED + "1 use only - click to activate");
        loreLines.add(ChatColor.GRAY + "+1 warp effect: " + effect.getName());
        meta.setLore(loreLines);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.DURABILITY,1,true);
        effectStone.setItemMeta(meta);
        return effectStone;
    }

    private final Effect effect;
}
