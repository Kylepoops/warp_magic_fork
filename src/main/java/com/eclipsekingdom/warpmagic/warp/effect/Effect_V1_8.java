package com.eclipsekingdom.warpmagic.warp.effect;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Random;

public class Effect_V1_8 implements IEffect {

    private static Random random = new Random();

    @Override
    public void playParticles(Player player) {
        Location location = player.getLocation().add(0, 1, 0);
        for (int i = 0; i < 15; i++) {
            double offX = randomDouble(-0.5, 0.5);
            double offY = randomDouble(-0.5, 0.5);
            double offZ = randomDouble(-0.5, 0.5);
            location.getWorld().playEffect(location.clone().add(offX, offY, offZ), Effect.valueOf("HAPPY_VILLAGER"), 2);
        }
    }

    public static double randomDouble(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

}
