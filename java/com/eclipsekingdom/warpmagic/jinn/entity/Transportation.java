package com.eclipsekingdom.warpmagic.jinn.entity;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.jinn.Jinn;
import com.eclipsekingdom.warpmagic.util.CustomSpawn;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Transportation {

    private static Set<UUID> activeTransporters = new HashSet<>();
    public static boolean isTransporter(Entity entity){
        return (activeTransporters.contains(entity.getUniqueId()));
    }
    public static void removeTranporters(){
        for(UUID entityID : activeTransporters){
            Entity entity = Bukkit.getEntity(entityID);
            if(entity != null){
                entity.remove();
            }
        }
    }

    private Chicken floating;
    private Vex flying;
    private MovementType mode;
    private Set<UUID> transports = new HashSet<>();
    public boolean isTransport(Entity entity){
        return transports.contains(entity.getUniqueId());
    }

    public enum MovementType{
        FLOATING, FLYING;
    }

    public Transportation(Location location){
        this.mode = MovementType.FLOATING;
        this.floating =  createFloating(location);
        activeTransporters.add(floating.getUniqueId());
        transports.add(floating.getUniqueId());
    }

    public Chicken getFloating(){
        return floating;
    }

    public Vex getFlying(){
        return flying;
    }

    public LivingEntity getCurrentTransporter(){
        if(mode == MovementType.FLOATING){
            return floating;
        }else{
            return flying;
        }
    }

    public void teleport(Location location){
        if(mode == MovementType.FLOATING){
            destroy(floating);
            floating = createFloating(location);
        }else{
            destroy(flying);
            flying = createFlying(location);
        }
    }

    public MovementType getMode(){
        return mode;
    }

    public void toggleMode(Jinn jinn){
        JinnAnatomy anatomy = jinn.getAnatomy();
        if(mode == MovementType.FLOATING){
            mode = MovementType.FLYING;
            flying = createFlying(floating.getLocation());
            destroy(floating);
            anatomy.setVehicle(flying);
        }else{
            mode = MovementType.FLOATING;
            floating = createFloating(flying.getLocation());
            destroy(flying);
            anatomy.setVehicle(floating);
        }
    }

    public void destroyComponents(){
        destroy(floating);
        destroy(flying);
    }

    private void destroy(Entity entity){
        if(entity != null){
            activeTransporters.remove(entity.getUniqueId());
            transports.remove(entity.getUniqueId());
            entity.remove();
        }
    }

    private Chicken createFloating(Location location){
        Chicken floating = (Chicken) CustomSpawn.spawn(location, EntityType.CHICKEN);
        CustomSpawn.makeInvisible(floating);
        floating.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 72000, 2,false, false), true);
        floating.setSilent(true);
        floating.setMetadata(CustomSpawn.nonHitBoxKey, new FixedMetadataValue(WarpMagic.plugin, true));
        floating.setMetadata(CustomSpawn.inflammableKey, new FixedMetadataValue(WarpMagic.plugin, true));
        activeTransporters.add(floating.getUniqueId());
        transports.add(floating.getUniqueId());
        return floating;
    }

    private Vex createFlying(Location location){
        Vex flying = (Vex) CustomSpawn.spawn(location, EntityType.VEX);
        CustomSpawn.makeInvisible(flying);
        flying.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 72000, 0,false, false), true);
        flying.setSilent(true);
        flying.setMetadata(CustomSpawn.nonHitBoxKey, new FixedMetadataValue(WarpMagic.plugin, true));
        flying.setMetadata(CustomSpawn.inflammableKey, new FixedMetadataValue(WarpMagic.plugin, true));
        flying.setMetadata(CustomSpawn.plotAttackKey, new FixedMetadataValue(WarpMagic.plugin, true));
        flying.setCustomName("Jinn");
        BukkitRunnable r = new BukkitRunnable() {
            @Override
            public void run() {
                JinnAnatomy.strip(flying.getEquipment());
            }
        };
        r.runTaskLater(WarpMagic.plugin, 1);
        activeTransporters.add(flying.getUniqueId());
        transports.add(flying.getUniqueId());
        return flying;
    }

}
