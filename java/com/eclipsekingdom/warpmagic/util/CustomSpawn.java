package com.eclipsekingdom.warpmagic.util;

import com.eclipsekingdom.warpmagic.jinn.Jinn;
import com.eclipsekingdom.warpmagic.jinn.theme.JinnTheme;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class CustomSpawn {

    public static List<Location> locations = new ArrayList<>();
    public static final String customKey = "pluginMob";
    public static final String inflammableKey = "noFire";
    public static final String nonHitBoxKey = "nonHitBoxComponent";
    public static final String plotAttackKey = "plotAttackKey";
    public static boolean spawningJinn = false;

    public static Entity spawn(Location location, EntityType entityType){
        locations.add(location);
        Entity entity = location.getWorld().spawnEntity(location, entityType);
        locations.remove(location);
        return entity;
    }

    public static void makeInvisible(LivingEntity entity){
        entity.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 72000, 0, false, false));
    }

    public static void spawnJinn(JinnTheme jinnTheme, Location location){
        spawningJinn = true;
        new Jinn(jinnTheme, location);
        spawningJinn = false;
    }

}
