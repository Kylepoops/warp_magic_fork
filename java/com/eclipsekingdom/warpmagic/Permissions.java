package com.eclipsekingdom.warpmagic;

import org.bukkit.entity.Player;

public class Permissions {

    public static boolean canSummonLoot(Player player){
        return hasPermission(player, LOOT_PERM);
    }

    public static boolean hasNoWarpLimit(Player player){
        return hasPermission(player, NO_WARP_LIMIT_PERM);
    }

    public static boolean hasNoVortexLimit(Player player){
        return hasPermission(player, NO_VORTEX_LIMIT_PERM);
    }

    public static boolean canBypassLimit(Player player){
        return hasPermission(player, BYPASS_PERM);
    }

    public static boolean canSetGlobalPoints(Player player){
        return hasPermission(player, SET_GLOBAL_PERM);
    }

    public static boolean hasExtras(Player player){
        return hasPermission(player, EXTRAS_PERM);
    }

    public static boolean hasAllEffects(Player player){
        return hasPermission(player, EFFECTS_PERM);
    }

    private static final String LOOT_PERM = "warpmagic.loot";
    private static final String NO_WARP_LIMIT_PERM = "warp.nolimit";
    private static final String NO_VORTEX_LIMIT_PERM = "vortex.nolimit";
    private static final String BYPASS_PERM = "warpmagic.bypass";
    private static final String SET_GLOBAL_PERM = "warpmagic.set";
    private static final String EXTRAS_PERM = "warpmagic.extras";
    private static final String EFFECTS_PERM = "warpmagic.effects";

    private static boolean hasPermission(Player player, String permString){
        return (player.hasPermission("warpmagic.*") || player.hasPermission(permString));
    }

}
