package com.eclipsekingdom.warpmagic;

import org.bukkit.entity.Player;

public class Permissions {

    public static boolean canSummonWarpStone(Player player){
        return hasPermission(player, WARP_STONE_PERM);
    }
    public static boolean hasNoWarpLimit(Player player){
        return hasPermission(player, NO_WARP_LIMIT_PERM);
    }


    private static final String WARP_STONE_PERM = "warp.stone";
    private static final String NO_WARP_LIMIT_PERM = "warp.nolimit";

    private static boolean hasPermission(Player player, String permString){
        return (player.hasPermission("warpmagic.*") || player.hasPermission(permString));
    }

}
