package com.eclipsekingdom.warpmagic.effect.list;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.effect.Effect;
import com.eclipsekingdom.warpmagic.util.RandomLocation;
import org.bukkit.*;
import org.bukkit.entity.Bat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;

public class Bats extends Effect {

    public static void removeEntities(){
        for(World world: Bukkit.getWorlds()){
            for(Entity entity: world.getEntities()){
                if(entity.hasMetadata(BAT_META)){
                    entity.remove();
                }
            }
        }
    }

    public Bats() {
        super(ChatColor.RED, "Bats", Material.BAT_SPAWN_EGG);
    }

    @Override
    public void run(Player player, WarpMagic plugin) {
        player.getWorld().spawnParticle(Particle.SQUID_INK,player.getLocation().add(0,1,0),15,0.5,0.8,0.5,0.01f);
        player.getWorld().playSound(player.getLocation(), Sound.ENTITY_BAT_TAKEOFF,0.5f,0.5f);
        Location origin = player.getLocation().add(0,1.8,0);
        List<Bat> bats = new ArrayList<>();
        for(int i = 0; i<BAT_COUNT;i++){
            Bat bat = (Bat) player.getWorld().spawnEntity(RandomLocation.getNear_3D(origin,1), EntityType.BAT);
            bat.setMetadata(BAT_META, new FixedMetadataValue(plugin, true));
            bats.add(bat);
        }
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                for(Bat bat: bats){
                    bat.remove();
                }
            }
        }, 20*BAT_DURATION);
    }

    private static final String BAT_META = "warpBat";
    private static final int BAT_COUNT = 5;
    private static final int BAT_DURATION = 3; //seconds
}
