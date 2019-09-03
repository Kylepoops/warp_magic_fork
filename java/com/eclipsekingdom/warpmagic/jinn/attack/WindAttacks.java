package com.eclipsekingdom.warpmagic.jinn.attack;

import com.eclipsekingdom.warpmagic.jinn.Jinn;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.EvokerFangs;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.List;

public class WindAttacks extends Attacks {

    private static final PotionEffect BLINDNESS = new PotionEffect(PotionEffectType.BLINDNESS, 4*20, 0, false, false);

    public WindAttacks() {
        super(1);
    }

    @Override
    public void runMelee(Jinn jinn, List<Entity> hitEntities) {
        playerMeleeEffect(jinn, jinn.getLocation());
        for(Entity entity: hitEntities){
            if(entity instanceof LivingEntity){
                LivingEntity livingEntity = (LivingEntity)entity;
                livingEntity.addPotionEffect(BLINDNESS);
                livingEntity.damage(6, jinn.getAnatomy().getAggro());
            }
        }
    }

    private void playerMeleeEffect(Jinn jinn, Location source){
        source.getWorld().playSound(source, Sound.ENTITY_EVOKER_FANGS_ATTACK, 0.5f, 0.5f);
        Vector center = source.toVector();
        World world = source.getWorld();
        for (int i = 0; i < 360; i += 360/9d) {
            double angle = (i * Math.PI / 180);
            double x = meleeRange * 1.5 * Math.cos(angle);
            double z = meleeRange * 1.5 * Math.sin(angle);
            Location loc = new Vector(center.getX() + x, center.getY(), center.getZ() + z).toLocation(world);
            EvokerFangs fangs = (EvokerFangs) world.spawnEntity(loc, EntityType.EVOKER_FANGS);
            fangs.setOwner(jinn.getAnatomy().getAggro());
        }
    }

    @Override
    public void runRanged(Jinn dova) {

    }
}
