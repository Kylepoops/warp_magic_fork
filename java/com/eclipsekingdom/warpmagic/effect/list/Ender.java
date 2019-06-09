package com.eclipsekingdom.warpmagic.effect.list;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.effect.Effect;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class Ender extends Effect {
    public Ender() {
        super(ChatColor.DARK_PURPLE, "Ender", Material.ENDER_PEARL);
    }

    @Override
    public void run(Player player, WarpMagic plugin) {
        player.getWorld().spawnParticle(Particle.PORTAL,player.getLocation().add(0,1,0),75,0.5,0.5,0.5,2f);
    }
}
