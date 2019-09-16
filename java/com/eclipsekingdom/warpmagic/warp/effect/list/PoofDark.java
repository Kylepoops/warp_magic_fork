package com.eclipsekingdom.warpmagic.warp.effect.list;

import com.eclipsekingdom.warpmagic.warp.effect.Effect;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class PoofDark extends Effect {
    public PoofDark() {
        super(ChatColor.DARK_GRAY, "Dark Poof", Material.INK_SAC);
    }

    @Override
    public void run(Player player) {
        player.getWorld().spawnParticle(Particle.SMOKE_LARGE, player.getLocation().add(0,1,0), 35, 0.5, 0.5, 0.5, 0.05f);
    }
}
