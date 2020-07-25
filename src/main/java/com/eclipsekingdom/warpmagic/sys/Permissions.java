package com.eclipsekingdom.warpmagic.sys;

import com.eclipsekingdom.warpmagic.sys.config.PluginConfig;
import com.eclipsekingdom.warpmagic.util.PermInfo;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.Set;

public class Permissions {

    private static final String LOOT_PERM = "warpmagic.loot";
    private static final String SET_GLOBAL_PERM = "warpmagic.global";
    private static final String EXTRAS_PERM = "warpmagic.extras";

    public static boolean canSummonLoot(CommandSender sender) {
        return hasPermission(sender, LOOT_PERM);
    }

    public static boolean canSetGlobalPoints(Player player) {
        return hasPermission(player, SET_GLOBAL_PERM);
    }

    public static boolean hasExtras(Player player) {
        return hasPermission(player, EXTRAS_PERM);
    }

    public static PermInfo getPermInfo(Player player) {
        Set<PermissionAttachmentInfo> perms = player.getEffectivePermissions();
        int bonusWarp = 0;
        int bonusVortex = 0;
        int maxWarp = PluginConfig.getMaxWarpNum();
        int maxVortex = PluginConfig.getMaxVortexNum();
        int cooldown = PluginConfig.getCooldownSeconds();
        int chargeUp = PluginConfig.getChargeUpSeconds();
        for (PermissionAttachmentInfo perm : perms) {
            String permString = perm.getPermission();
            try {
                if (permString.startsWith("warp.bonus.")) {
                    int newBonus = Integer.parseInt(permString.split("warp\\.bonus\\.")[1]);
                    if (newBonus > bonusWarp) bonusWarp = newBonus;
                } else if (permString.startsWith("warp.cap.")) {
                    int newCap = Integer.parseInt(permString.split("warp\\.cap\\.")[1]);
                    if (newCap > maxWarp) maxWarp = newCap;
                } else if (permString.startsWith("vortex.bonus.")) {
                    int newBonus = Integer.parseInt(permString.split("vortex\\.bonus\\.")[1]);
                    if (newBonus > bonusVortex) bonusVortex = newBonus;
                } else if (permString.startsWith("vortex.cap.")) {
                    int newCap = Integer.parseInt(permString.split("vortex\\.cap\\.")[1]);
                    if (newCap > maxVortex) maxVortex = newCap;
                } else if (permString.startsWith("warpmagic.cooldown.")) {
                    int newTime = Integer.parseInt(permString.split("warpmagic\\.cooldown\\.")[1]);
                    if (newTime < cooldown) cooldown = newTime;
                } else if (permString.startsWith("warpmagic.chargeup.")) {
                    int newTime = Integer.parseInt(permString.split("warpmagic\\.chargeup\\.")[1]);
                    if (newTime < chargeUp) chargeUp = newTime;
                }
            } catch (Exception e) {
                //do nothing
            }

        }

        return new PermInfo(bonusWarp, maxWarp, bonusVortex, maxVortex, chargeUp, cooldown);
    }

    private static boolean hasPermission(CommandSender sender, String permString) {
        return (sender.hasPermission("warpmagic.*") || sender.hasPermission(permString));
    }

}