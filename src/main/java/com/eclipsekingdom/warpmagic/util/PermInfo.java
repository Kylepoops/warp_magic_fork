package com.eclipsekingdom.warpmagic.util;

public class PermInfo {

    private int warpBonus;
    private int warpMax;
    private int vortexBonus;
    private int vortexMax;
    private int chargeUp;
    private int cooldown;

    public PermInfo(int warpBonus, int warpMax, int vortexBonus, int vortexMax, int chargeUp, int cooldown) {
        this.warpBonus = warpBonus;
        this.warpMax = warpMax > 0 ? warpMax : 0;
        this.vortexBonus = vortexBonus;
        this.vortexMax = vortexMax > 0 ? vortexMax : 0;
        this.chargeUp = chargeUp > 0 ? chargeUp : 0;
        this.cooldown = cooldown > 0 ? cooldown : 0;
    }

    public int getWarpBonus() {
        return warpBonus;
    }

    public int getWarpMax() {
        return warpMax;
    }

    public int getVortexBonus() {
        return vortexBonus;
    }

    public int getVortexMax() {
        return vortexMax;
    }

    public int getChargeUp() {
        return chargeUp;
    }

    public int getCooldown() {
        return cooldown;
    }

}
