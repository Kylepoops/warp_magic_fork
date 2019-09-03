package com.eclipsekingdom.warpmagic.jinn.attack;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.jinn.Jinn;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public abstract class Attacks {

    private boolean meleeCooldown = false;
    private boolean rangedCooldown = false;
    private boolean abilityCooldown = false;

    protected double meleeRange;

    public Attacks(double meleeRange){
        this.meleeRange = meleeRange;
    }

    public void attemptMelee(Jinn jinn){
        if(!meleeCooldown){
            List<Entity> hitEntities = jinn.getNearbyEntities(meleeRange,meleeRange,meleeRange);
            for(Entity entity: hitEntities){
                if(entity instanceof Player){
                    processMelee(jinn, hitEntities);
                    return;
                }else if(entity instanceof Monster){
                    Monster monster = (Monster) entity;
                    if(monster.getTarget() != null){
                        if(jinn.getAnatomy().isBodyPart(monster.getTarget())){
                            processMelee(jinn, hitEntities);
                            return;
                        }
                    }
                }
            }
        }
    }

    private void processMelee(Jinn jinn, List<Entity> hitEntities){
        jinn.interact();
        runMelee(jinn, hitEntities);
        meleeCooldown = true;
        BukkitRunnable r = new BukkitRunnable() {
            @Override
            public void run() {
                meleeCooldown = false;
            }
        };
        r.runTaskLater(WarpMagic.plugin, 20 * 3);
    }

    public abstract void runMelee(Jinn jinn, List<Entity> hitEntities);

    public void attemptRanged(Jinn jinn){
        if(!rangedCooldown){
            LivingEntity target = jinn.getAnatomy().getAggro().getTarget();
            if(target != null){
                processRanged(jinn);
            }
        }
    }

    private void processRanged(Jinn jinn){
        jinn.interact();
        runRanged(jinn);
        rangedCooldown = true;
        BukkitRunnable r = new BukkitRunnable() {
            @Override
            public void run() {
                rangedCooldown = false;
            }
        };
        r.runTaskLater(WarpMagic.plugin, 20 * 6);
    }

    public abstract void runRanged(Jinn dova);

    public boolean onMeleecColdown() {
        return meleeCooldown;
    }

    public boolean onRangedCooldown() {
        return rangedCooldown;
    }

    public boolean onAbilityCooldown() {
        return abilityCooldown;
    }
}
