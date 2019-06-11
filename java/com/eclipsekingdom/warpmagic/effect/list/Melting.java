package com.eclipsekingdom.warpmagic.effect.list;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.effect.Effect;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class Melting extends Effect {

    public Melting() {
        super(ChatColor.AQUA, "Melting", Material.WATER_BUCKET);
    }

    @Override
    public void run(Player player, WarpMagic plugin) {
        player.getWorld().spawnParticle(Particle.FALLING_WATER,player.getLocation().add(0,1,0),35,0.5,0.77,0.5,2f);
    }
}
