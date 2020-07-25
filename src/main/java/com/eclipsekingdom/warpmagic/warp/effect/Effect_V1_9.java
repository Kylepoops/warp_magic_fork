package com.eclipsekingdom.warpmagic.warp.effect;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Effect_V1_9 implements IEffect {

    @Override
    public void playParticles(Player player) {
        player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation().add(0, 1, 0), 15, 0.5, 0.5, 0.5, 2f);
    }
}
