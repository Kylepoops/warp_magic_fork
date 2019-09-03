package com.eclipsekingdom.warpmagic.jinn;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.jinn.entity.JinnAnatomy;
import com.eclipsekingdom.warpmagic.jinn.entity.Transportation;
import com.eclipsekingdom.warpmagic.jinn.theme.JinnTheme;
import com.eclipsekingdom.warpmagic.util.CustomSpawn;
import com.google.common.collect.ImmutableSet;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Biome;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.*;

public class JinnListener implements Listener {

    private Random random = new Random();
    private WarpMagic plugin;

    public JinnListener(){
        this.plugin = WarpMagic.plugin;
        plugin.getServer().getPluginManager().registerEvents(this, WarpMagic.plugin);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onDamage(EntityDamageEvent e){
        Entity entity = e.getEntity();
        if(!Transportation.isTransporter(entity)){
            if(JinnAnatomy.isAnatomy(entity)){
                e.setCancelled(true);
                if(!invalidReason(e.getCause())){
                    e.setDamage(e.getDamage() * 0.5);
                    Jinn jinn = JinnAnatomy.getJinn(entity);
                    if(jinn != null && jinn.isValid()){
                        jinn.hurt(e.getFinalDamage());
                        jinn.interact();
                        if(jinn.isDead()){
                            int lootingLevel = 0;
                            if(e instanceof EntityDamageByEntityEvent){
                                lootingLevel = getLootingLevel((EntityDamageByEntityEvent)e);
                            }
                            jinn.dropLoot(random, lootingLevel);
                            jinn.remove();
                        }
                    }
                }
            }
        }else{
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e){
        Entity entity = e.getEntity();
        if(JinnAnatomy.isAnatomy(entity)){
            Jinn jinn = JinnAnatomy.getJinn(entity);
            LivingEntity livingEntity = getLivingEntity(e);
            if(jinn != null && jinn.isValid() && livingEntity != null){
                if(random.nextDouble() <= jinnTeleportAttackerChance)
                jinn.teleportAttacker(livingEntity, random);
            }
        }
    }

    public LivingEntity getLivingEntity(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof LivingEntity){
            return (LivingEntity) e.getDamager();
        }else{
            if(e.getDamager() instanceof Projectile){
                Projectile projectile = (Projectile) e.getDamager();
                if(projectile.getShooter() instanceof LivingEntity){
                    return (LivingEntity) projectile.getShooter();
                }else{
                    return null;
                }
            }else{
                return null;
            }
        }
    }

    private static double jinnTeleportAttackerChance = 0.15;

    private int getLootingLevel(EntityDamageByEntityEvent e){
        int level = 0;
        if(e.getDamager() instanceof Player){
            Player attacker = (Player) e.getDamager();
            ItemStack handItem = attacker.getInventory().getItemInMainHand();
            if(handItem != null && handItem.hasItemMeta() && handItem.getItemMeta().hasEnchants() && handItem.getItemMeta().hasEnchant(Enchantment.LOOT_BONUS_MOBS)){
                level = handItem.getItemMeta().getEnchantLevel(Enchantment.LOOT_BONUS_MOBS);
            }
        }
        return level;
    }

    private boolean invalidReason(EntityDamageEvent.DamageCause cause){
        return (cause == EntityDamageEvent.DamageCause.FALL || cause == EntityDamageEvent.DamageCause.SUFFOCATION);
    }


    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e){
        if(e.getRightClicked().getType() == EntityType.ARMOR_STAND && JinnAnatomy.isAnatomy(e.getRightClicked())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEgg(EntityDropItemEvent e){
        if(JinnAnatomy.isAnatomy(e.getEntity()) && e.getItemDrop().getType() == EntityType.EGG){
            e.setCancelled(true);
        }
    }

    @EventHandler (priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onSpawn(EntitySpawnEvent e){
        Entity entity = e.getEntity();
        if(CustomSpawn.locations.contains(e.getLocation())){
            entity.setMetadata(CustomSpawn.customKey, new FixedMetadataValue(plugin, true));
        }else if((entity.getType() == EntityType.ZOMBIE || entity.getType() == EntityType.HUSK) && !CustomSpawn.spawningJinn && !alreadyConverting.contains(entity.getUniqueId())){
            /*
            Location location = entity.getLocation();
            Biome biome = location.getWorld().getBiome(location.getBlockX(), location.getBlockZ());

            double spawnRate = dovaConfig.getSpawnRate();

            if(mountains.contains(biome) && location.getY() >= 70){
                if(random.nextDouble() <= spawnRate){
                    convertToDragon(entity, DovaTheme.random());
                }
            }else{
                spawnRate *= 0.1;
                if(random.nextDouble() <= spawnRate){
                    if(fireBiomes.contains(biome)){
                        convertToDragon(entity, DovaTheme.from(DovaTheme.DovaThemeType.FIRE));
                    }else if(iceBiomes.contains(biome)){
                        convertToDragon(entity, DovaTheme.from(DovaTheme.DovaThemeType.ICE));
                    }else if(poisonBiomes.contains(biome)){
                        convertToDragon(entity, DovaTheme.from(DovaTheme.DovaThemeType.POISON));
                    }
                }
            }
            */
        }
    }

    private ImmutableSet<Biome> jinnRegions = new ImmutableSet.Builder<Biome>()
            .add(Biome.DARK_FOREST)
            .build();


    private void convertToJinn(Entity entity, JinnTheme type){
        alreadyConverting.add(entity.getUniqueId());
        BukkitRunnable r = new BukkitRunnable() {
            @Override
            public void run() {
                alreadyConverting.remove(entity.getUniqueId());
            }
        };
        r.runTaskLater(plugin,5);
        CustomSpawn.spawnJinn(type, entity.getLocation());
        entity.remove();
    }

    private static ArrayList<UUID> alreadyConverting = new ArrayList<>();

    @EventHandler
    public void onPickup(EntityPickupItemEvent e){
        if(JinnAnatomy.isAnatomy(e.getEntity())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEffect(EntityPotionEffectEvent e){
        if(e.getEntity().hasMetadata(CustomSpawn.nonHitBoxKey)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDismount(EntityDismountEvent e){
        if(JinnAnatomy.isAnatomy(e.getEntity())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBat(BatToggleSleepEvent e){
        if(!e.isAwake() && (JinnAnatomy.isAnatomy(e.getEntity()) || Transportation.isTransporter(e.getEntity()))){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onUnload(ChunkUnloadEvent e){
        for(Entity entity: e.getChunk().getEntities()){
            if(JinnAnatomy.isAnatomy(entity)){
                unloadedJinnParts.add(entity.getUniqueId());
            }
        }
    }

    private Set<UUID> unloadedJinnParts = new HashSet<>();

    @EventHandler
    public void onLoad(ChunkLoadEvent e){
        for(Entity entity: e.getChunk().getEntities()){
            if(unloadedJinnParts.contains(entity.getUniqueId()) && !JinnAnatomy.isAnatomy(entity)){
                entity.remove();
            }
        }
    }


    @EventHandler
    public void onCombust(EntityCombustEvent e){
        if(e.getEntity().hasMetadata(CustomSpawn.inflammableKey)){
            e.setCancelled(true);
        }
    }



}
