package com.eclipsekingdom.warpmagic.warp.effect.list;

import com.eclipsekingdom.warpmagic.warp.effect.Effect;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class Flame extends Effect {
    public Flame() {
        super(ChatColor.GOLD,"Flame", Material.BLAZE_POWDER);
    }

    @Override
    public void run(Player player) {
        player.getWorld().spawnParticle(Particle.FLAME,player.getLocation().add(0,1,0),45,0.5,0.5,0.5,0.1f);
        player.getWorld().spawnParticle(Particle.SMOKE_LARGE,player.getLocation().add(0,1,0),9,0.5,0.5,0.5,0.1f);
        player.getWorld().playSound(player.getLocation(), Sound.ITEM_FIRECHARGE_USE,0.5f,1f);
    }
}
