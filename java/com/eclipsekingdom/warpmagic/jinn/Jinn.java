package com.eclipsekingdom.warpmagic.jinn;

import com.eclipsekingdom.warpmagic.Teleportation;
import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.jinn.attack.Attacks;
import com.eclipsekingdom.warpmagic.jinn.cloak.JinnCloak;
import com.eclipsekingdom.warpmagic.jinn.entity.JinnAnatomy;
import com.eclipsekingdom.warpmagic.jinn.entity.Transportation;
import com.eclipsekingdom.warpmagic.jinn.head.JinnHead;
import com.eclipsekingdom.warpmagic.jinn.shield.JinnShield;
import com.eclipsekingdom.warpmagic.jinn.theme.JinnTheme;
import com.eclipsekingdom.warpmagic.util.CustomSpawn;
import com.eclipsekingdom.warpmagic.warps.warp.WarpStone;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Jinn {

    private static Map<UUID, Jinn> IDToJinn = new HashMap<>();
    public static Jinn fromID(UUID ID){
        return IDToJinn.get(ID);
    }

    private UUID ID;
    private JinnLife life;

    private JinnHead head;
    private JinnShield shield;
    private JinnCloak cloak;
    private JinnAnatomy anatomy;
    private Transportation transportation;
    private Attacks attacks;
    private double health = 66;
    private boolean hurting = false;
    private JinnTheme.JinnThemeType type;
    private JinnTheme theme;

    public Jinn(JinnTheme theme, Location location){
        ID = UUID.randomUUID();
        this.head = theme.getHead();
        this.attacks = theme.getAttacks();
        this.shield = theme.getShield();
        this.cloak = theme.getCloak();
        this.transportation = new Transportation(location);
        this.anatomy = new JinnAnatomy(location, ID, theme);
        anatomy.setVehicle(transportation.getFloating());
        IDToJinn.put(ID, this);
        life = new JinnLife(this);
        life.start();
        this.type = theme.getType();
        this.theme = theme;
    }

    public void interact(){
        life.resetInactiveTimer();
    }

    public void hurt(double damage){
        if(!hurting){
            anatomy.hurt(this);
            Location location = getLocation();
            location.getWorld().playSound(location, Sound.ENTITY_BLAZE_HURT, 0.7f,0.5f);
            health -= damage;
            hurting = true;
            BukkitRunnable r = new BukkitRunnable() {
                @Override
                public void run() {
                    hurting = false;
                }
            };
            r.runTaskLater(WarpMagic.plugin, 10);
        }
    }

    public boolean isHurting(){
        return hurting;
    }

    public UUID getID() {
        return ID;
    }

    public JinnTheme.JinnThemeType getType() {
        return type;
    }

    public boolean isDead(){
        return health <= 0;
    }

    public Location getLocation() {
        return anatomy.getBody().getLocation();
    }

    public World getWorld() {
        return anatomy.getBody().getWorld();
    }

    public JinnAnatomy getAnatomy(){
        return anatomy;
    }

    public JinnHead getHead() {
        return head;
    }

    public JinnShield getShield(){
        return shield;
    }

    public JinnCloak getScales() {
        return cloak;
    }

    public Attacks getAttacks(){
        return attacks;
    }

    public Transportation getTransportation(){
        return transportation;
    }

    public List<Entity> getNearbyEntities(double x, double y, double z){
        List<Entity> uncleanedNearbyEntities = anatomy.getBody().getNearbyEntities(x, y, z);
        List<Entity> nearbyEntities = new ArrayList<>();
        for(Entity entity: uncleanedNearbyEntities){
            if(!entity.hasMetadata(CustomSpawn.nonHitBoxKey) && !anatomy.isBodyPart(entity)){
                nearbyEntities.add(entity);
            }
        }
        return nearbyEntities;
    }

    public boolean isValid(){
        return anatomy.isValid();
    }

    public void remove(){
        IDToJinn.remove(ID);
        transportation.destroyComponents();
        anatomy.destroyComponents();
    }

    public void dropLoot(Random random, int lootingLevel){

        World world = getWorld();
        Location location = getLocation();
        world.playSound(location, Sound.ENTITY_BLAZE_DEATH, 0.7f, 0.5f);
        world.playSound(location, Sound.ENTITY_FIREWORK_ROCKET_TWINKLE_FAR, 2f ,2f);

        if(random.nextDouble() <= 1){
            world.dropItemNaturally(location, WarpStone.getInstance().asItem());
        }

        ExperienceOrb experienceOrb = (ExperienceOrb) location.getWorld().spawnEntity(location, EntityType.EXPERIENCE_ORB);
        experienceOrb.setExperience(100);

    }

    public void teleport(Random random, double x, double y, double z){
        Location loc = getLocation();
        Location newLoc = loc.add(random.nextDouble()*2*x - x, random.nextDouble() *2*y-y, random.nextDouble()*2*z -z);
        Teleportation.makeSafe(newLoc);
        playSound(loc);
        transportation.teleport(newLoc);
        anatomy.teleport(newLoc,theme , ID);
        BukkitRunnable r = new BukkitRunnable() {
            @Override
            public void run() {
                if(isValid()){
                    anatomy.setVehicle(transportation.getCurrentTransporter());
                    playSound(newLoc);
                }
            }
        };
        r.runTaskLater(WarpMagic.plugin, 10);
    }

    private static int attackerTeleportDistance = 9;

    public void teleportAttacker(LivingEntity attacker, Random random){
        double x = random.nextDouble()*2*attackerTeleportDistance - attackerTeleportDistance;
        double y = random.nextDouble()*2*attackerTeleportDistance - attackerTeleportDistance;
        double z = random.nextDouble()*2*attackerTeleportDistance - attackerTeleportDistance;
        Location newLoc = attacker.getLocation().add(x, y, z);
        Teleportation.makeNotUnderground(newLoc);
        playSound(attacker.getLocation());
        attacker.teleport(newLoc);
        attacker.damage(3, anatomy.getAggro());
        attacker.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, (int)(20*4.5), 3, false, false),true);
        playSound(newLoc);
    }


    private void playSound(Location location){
        location.getWorld().playSound(location, Sound.ENTITY_ENDERMAN_TELEPORT, 0.5f, 1.3f);
    }

}
