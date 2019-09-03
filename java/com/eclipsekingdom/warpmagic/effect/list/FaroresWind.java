package com.eclipsekingdom.warpmagic.effect.list;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.effect.Effect;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class FaroresWind extends Effect {

    public FaroresWind() {
        super(ChatColor.GREEN, "Farore's WindHurtShieldBuilder", Material.EMERALD);
    }

    @Override
    public void run(Player player, WarpMagic plugin) {
        player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY,player.getLocation().add(0,1,0),25,0.5,0.5,0.5,2f);
    }
}
