package com.eclipsekingdom.warpmagic.jinn.entity;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.jinn.Jinn;
import com.eclipsekingdom.warpmagic.jinn.cloak.JinnCloak;
import com.eclipsekingdom.warpmagic.jinn.head.JinnHead;
import com.eclipsekingdom.warpmagic.jinn.shield.JinnShield;
import com.eclipsekingdom.warpmagic.jinn.theme.JinnTheme;
import com.eclipsekingdom.warpmagic.util.CustomSpawn;
import com.eclipsekingdom.warpmagic.util.Heads;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class JinnAnatomy {

    private static Map<UUID, UUID> activeAnatomyToJinn = new HashMap<>();
    public static boolean isAnatomy(Entity entity){
        return (activeAnatomyToJinn.containsKey(entity.getUniqueId()));
    }
    public static Jinn getJinn(Entity entity){
        return Jinn.fromID(activeAnatomyToJinn.get(entity.getUniqueId()));
    }
    public static void removeAllDovaAnatomy(){
        for(UUID entityID : activeAnatomyToJinn.keySet()){
            Entity entity = Bukkit.getEntity(entityID);
            if(entity != null){
                entity.remove();
            }
        }
    }

    private ArmorStand body;
    private ArmorStand tail;
    private Zombie aggro;
    private Set<UUID> bodyParts = new HashSet<>();
    public static String bloodKey = "bloodColor";
    public static String tierKey = "mobTier";

    public JinnAnatomy(Location spawnLocation, UUID jinnID, JinnTheme theme){
        this.body = spawnBody(spawnLocation, theme);
        registerPart(body.getUniqueId(), jinnID);

        this.tail = spawnTail(spawnLocation, theme);
        registerPart(tail.getUniqueId(), jinnID);

        this.aggro = spawnAggro(spawnLocation);
        registerPart(aggro.getUniqueId(), jinnID);

    }

    private void registerPart(UUID bodyPartID, UUID dovaID){
        activeAnatomyToJinn.put(bodyPartID, dovaID);
        bodyParts.add(bodyPartID);
    }

    public void setVehicle(Entity vehicle){
        vehicle.addPassenger(body);
        vehicle.addPassenger(tail);
        vehicle.addPassenger(aggro);
    }

    public void destroyComponents(){
        destroy(body);
        destroy(tail);
        destroy(aggro);
    }

    public boolean isValid(){
        return body.isValid() && tail.isValid() && aggro.isValid();
    }

    private void destroy(Entity entity){
        if(entity != null){
            activeAnatomyToJinn.remove(entity.getUniqueId());
            entity.remove();
        }
    }

    public void teleport(Location location, JinnTheme theme, UUID jinnID){
        destroyComponents();
        this.body = spawnBody(location, theme);
        registerPart(body.getUniqueId(), jinnID);

        this.tail = spawnTail(location, theme);
        registerPart(tail.getUniqueId(), jinnID);

        this.aggro = spawnAggro(location);
        registerPart(aggro.getUniqueId(), jinnID);
    }

    public ArmorStand getBody(){
        return body;
    }

    public ArmorStand getTail(){
        return body;
    }

    public Zombie getAggro(){
        return aggro;
    }

    public boolean isBodyPart(Entity entity){
        return (bodyParts.contains(entity.getUniqueId()));
    }


    public void hurt(Jinn jinn){
        JinnHead head = jinn.getHead();
        JinnCloak cloak = jinn.getScales();
        JinnShield shield = jinn.getShield();
        ItemStack shieldHurt = shield.getHurt();
        ItemStack shieldNeutral = shield.getNeutral();
        body.getEquipment().setChestplate(cloak.getHurt());
        body.getEquipment().setHelmet(head.getHurt());
        body.getEquipment().setItemInMainHand(shieldHurt);
        body.getEquipment().setItemInOffHand(shieldHurt);
        tail.setChestplate(cloak.getHurt());
        tail.setLeggings(cloak.getHurtTail());
        tail.getEquipment().setItemInMainHand(shieldHurt);
        tail.getEquipment().setItemInOffHand(shieldHurt);
        BukkitRunnable r = new BukkitRunnable() {
            @Override
            public void run() {
                if(jinn.isValid()){
                    body.getEquipment().setChestplate(cloak.getNeutral());
                    body.getEquipment().setHelmet(head.getNeutral());
                    body.getEquipment().setItemInMainHand(shieldNeutral);
                    body.getEquipment().setItemInOffHand(shieldNeutral);
                    tail.setChestplate(cloak.getNeutral());
                    tail.setLeggings(cloak.getNeutralTail());
                    tail.getEquipment().setItemInMainHand(shieldNeutral);
                    tail.getEquipment().setItemInOffHand(shieldNeutral);
                }
            }
        };
        r.runTaskLater(WarpMagic.plugin, 10);

    }

    private ArmorStand spawnBody(Location location, JinnTheme theme){
        ArmorStand body = (ArmorStand) CustomSpawn.spawn(location, EntityType.ARMOR_STAND);
        body.setArms(true);
        body.getEquipment().setChestplate(theme.getCloak().getNeutral());
        ItemStack shield = theme.getShield().getNeutral();
        body.getEquipment().setItemInMainHand(shield);
        body.getEquipment().setItemInOffHand(shield);
        body.getEquipment().setHelmet(Heads.JINN.getItemStack());
        body.setBasePlate(false);
        body.setVisible(false);
        body.setMetadata(bloodKey, new FixedMetadataValue(WarpMagic.plugin, Color.GREEN));
        body.setMetadata(tierKey, new FixedMetadataValue(WarpMagic.plugin, 5.0));
        body.setMetadata(CustomSpawn.plotAttackKey, new FixedMetadataValue(WarpMagic.plugin, true));
        body.setCustomName("Jinn");
        return body;
    }

    private ArmorStand spawnTail(Location location, JinnTheme theme){
        ArmorStand tail = (ArmorStand) CustomSpawn.spawn(location, EntityType.ARMOR_STAND);
        tail.setArms(true);
        tail.getEquipment().setChestplate(theme.getCloak().getNeutral());
        tail.getEquipment().setLeggings(theme.getCloak().getNeutralTail());
        ItemStack shield = theme.getShield().getNeutral();
        tail.getEquipment().setItemInMainHand(shield);
        tail.getEquipment().setItemInOffHand(shield);
        tail.setBasePlate(false);
        tail.setSmall(true);
        tail.setVisible(false);
        tail.setMetadata(bloodKey, new FixedMetadataValue(WarpMagic.plugin, Color.GREEN));
        tail.setMetadata(tierKey, new FixedMetadataValue(WarpMagic.plugin, 5.0));
        tail.setMetadata(CustomSpawn.plotAttackKey, new FixedMetadataValue(WarpMagic.plugin, true));
        tail.setCustomName("Jinn");
        return tail;
    }

    private Zombie spawnAggro(Location location){
        Zombie aggro = (Zombie) CustomSpawn.spawn(location, EntityType.ZOMBIE);
        CustomSpawn.makeInvisible(aggro);
        aggro.setSilent(true);
        aggro.setBaby(false);
        aggro.setCustomName("Jinn");
        aggro.setMetadata(CustomSpawn.inflammableKey, new FixedMetadataValue(WarpMagic.plugin, true));
        aggro.setMetadata(bloodKey, new FixedMetadataValue(WarpMagic.plugin, Color.GREEN));
        aggro.setMetadata(tierKey, new FixedMetadataValue(WarpMagic.plugin, 5.0));
        if(aggro.isInsideVehicle()){
            aggro.getVehicle().remove();
        }
        BukkitRunnable r = new BukkitRunnable() {
            @Override
            public void run() {
                strip(aggro.getEquipment());
            }
        };
        r.runTaskLater(WarpMagic.plugin, 5);
        return aggro;
    }

    private static ItemStack air = new ItemStack(Material.AIR);
    public static void strip(EntityEquipment entityEquipment){
        entityEquipment.setHelmet(air);
        entityEquipment.setChestplate(air);
        entityEquipment.setLeggings(air);
        entityEquipment.setBoots(air);
        entityEquipment.setItemInMainHand(air);
        entityEquipment.setItemInOffHand(air);
    }

}
