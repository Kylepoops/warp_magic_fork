package com.eclipsekingdom.warpmagic.warp.effect.list;

import com.eclipsekingdom.warpmagic.warp.effect.Effect;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class FaroresWind extends Effect {

    public FaroresWind() {
        super(ChatColor.GREEN, "Farore's Wind", Material.EMERALD);
    }

    @Override
    public void run(Player player) {
        player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY,player.getLocation().add(0,1,0),25,0.5,0.5,0.5,2f);
    }
}
