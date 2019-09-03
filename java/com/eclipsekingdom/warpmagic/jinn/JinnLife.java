package com.eclipsekingdom.warpmagic.jinn;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.jinn.attack.Attacks;
import com.eclipsekingdom.warpmagic.jinn.entity.JinnAnatomy;
import com.eclipsekingdom.warpmagic.jinn.entity.Transportation;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JinnLife extends BukkitRunnable {

    private static List<JinnLife> lives = new ArrayList<>();
    public static void remvoeAllJinn(){
        for(int i = lives.size() -1; i >= 0; i--){
            lives.get(i).stop();
        }
    }

    private static int maxLife = 20 * 60 * 45;//45 minutes
    private static int inactiveDespawnTime = 20 * 60 * 8; //8 minutes

    private Jinn jinn;
    private Attacks attacks;
    private JinnAnatomy anatomy;
    private Transportation transportation;
    private ArmorStand body;
    private ArmorStand tail;
    private Zombie aggro;

    private int inactiveDespawnTimer = inactiveDespawnTime;
    public void resetInactiveTimer(){
        inactiveDespawnTimer = inactiveDespawnTime;
    }

    private int lifeClock = 0;
    private Random random = new Random();

    public JinnLife(Jinn jinn){
        this.jinn = jinn;
        this.attacks = jinn.getAttacks();
        this.anatomy = jinn.getAnatomy();
        this.transportation = jinn.getTransportation();
        this.body = anatomy.getBody();
        this.tail = anatomy.getTail();
        this.aggro = anatomy.getAggro();
        lives.add(this);
    }

    @Override
    public void run() {
        if(lifeClock <= maxLife && inactiveDespawnTimer > 0){
            if(jinn.isValid()){

                Vector direction = aggro.getLocation().getDirection();
                body.teleport(body.getLocation().setDirection(direction));
                tail.teleport(tail.getLocation().setDirection(direction));

                if(lifeClock % 100 == 0){
                    if(random.nextDouble() <= 0.55){
                        jinn.getWorld().playSound(jinn.getLocation(), Sound.ENTITY_PARROT_IMITATE_BLAZE, 0.7f, 0.5f);
                    }
                }
                if(lifeClock % 70 == 0){
                    if(random.nextDouble() <= 0.44){
                        jinn.getWorld().playSound(jinn.getLocation(), Sound.ENTITY_BLAZE_AMBIENT, 0.7f, 0.5f);
                    }
                }

                attacks.attemptMelee(jinn);
                attacks.attemptRanged(jinn);

                if((lifeClock+5)% 300==0){
                    transportation.toggleMode(jinn);
                    if(transportation.getMode() == Transportation.MovementType.FLOATING){
                        jinn.teleport(random,15,15,15);
                    }
                }

            }else{
                stop();
            }
        }else{
            stop();
        }

        lifeClock +=5;
        inactiveDespawnTimer -= 5;
    }


    public void start(){
        runTaskTimer(WarpMagic.plugin, 0, 5);
    }

    public void stop(){
        jinn.remove();
        cancel();
        lives.remove(this);
    }

}
