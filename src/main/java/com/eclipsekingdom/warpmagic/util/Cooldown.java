package com.eclipsekingdom.warpmagic.util;

import com.eclipsekingdom.warpmagic.WarpMagic;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class Cooldown extends BukkitRunnable {

    private int count;
    private Runnable r;

    public Cooldown(int count, Runnable r) {
        this.count = count;
        this.r = r;
        runTaskTimer(WarpMagic.getPlugin(), 20, 20);
    }

    @Override
    public void run() {
        count--;
        if (count <= 0) {
            cancel();
            r.run();
        }
    }

    public int getCount() {
        return count;
    }

}
