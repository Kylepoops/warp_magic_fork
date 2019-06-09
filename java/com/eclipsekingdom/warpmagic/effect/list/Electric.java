package com.eclipsekingdom.warpmagic.effect.list;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.effect.Effect;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Electric extends Effect {

    public Electric() {
        super(ChatColor.BLUE, "Electric", Material.TRIDENT);
    }

    @Override
    public void run(Player player, WarpMagic plugin) {
        player.getWorld().strikeLightningEffect(player.getLocation());
    }
}
