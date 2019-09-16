package com.eclipsekingdom.warpmagic.warp.effect.list;

import com.eclipsekingdom.warpmagic.warp.effect.Effect;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Electric extends Effect {

    public Electric() {
        super(ChatColor.BLUE, "Electric", Material.TRIDENT);
    }

    @Override
    public void run(Player player) {
        player.getWorld().strikeLightningEffect(player.getLocation());
    }
}
