package com.eclipsekingdom.warpmagic.effect.list;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.effect.Effect;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class PoofDark extends Effect {
    public PoofDark() {
        super(ChatColor.DARK_GRAY, "Dark Poof", Material.BLACK_DYE);
    }

    @Override
    public void run(Player player, WarpMagic plugin) {
        player.getWorld().spawnParticle(Particle.SMOKE_LARGE, player.getLocation().add(0,1,0), 35, 0.5, 0.5, 0.5, 0.05f);
    }
}
