package com.eclipsekingdom.warpmagic.effect.list;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.effect.Effect;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class Poof extends Effect {
    public Poof() {
        super(ChatColor.DARK_GRAY, "Poof", Material.SUGAR);
    }

    @Override
    public void run(Player player, WarpMagic plugin) {
        player.getWorld().spawnParticle(Particle.EXPLOSION_NORMAL, player.getLocation().add(0,1,0), 35, 0.5, 0.5, 0.5, 0.05f);
    }
}
