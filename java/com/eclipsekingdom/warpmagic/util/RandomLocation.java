package com.eclipsekingdom.warpmagic.util;

import org.bukkit.Location;

import java.util.Random;

public class RandomLocation {

    public static Location getNear_2D(Location location, int radius) {
        double xOffSet = getRandomWithRadius(radius);
        double zOffSet = getRandomWithRadius(radius);
        return location.clone().add(xOffSet, 0, zOffSet);
    }

    public static Location getNear_3D(Location location, int radius) {
        double xOffSet = getRandomWithRadius(radius);
        double yOffSet = getRandomWithRadius(radius);
        double zOffSet = getRandomWithRadius(radius);
        return location.clone().add(xOffSet, yOffSet, zOffSet);
    }


    private static final Random random = new Random();

    private static double getRandomWithRadius(int radius) {
        return ((random.nextDouble() * radius * 2) - radius);
    }

}
