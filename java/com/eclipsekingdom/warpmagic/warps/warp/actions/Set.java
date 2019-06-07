package com.eclipsekingdom.warpmagic.warps.warp.actions;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.warps.validation.LocationStatus;
import com.eclipsekingdom.warpmagic.warps.validation.LocationValidation;
import com.eclipsekingdom.warpmagic.warps.validation.NameStatus;
import com.eclipsekingdom.warpmagic.warps.validation.NameValidation;
import com.eclipsekingdom.warpmagic.warps.warp.Warp;
import com.eclipsekingdom.warpmagic.warps.warp.WarpAction;
import org.bukkit.entity.Player;

public class Set extends WarpAction {

    @Override
    public void run(Player player, String[] args) {

        if(args.length > 1){
            if(warpManager.getUsedWarpCount(player) < warpNumManager.getUnlockedWarpNum(player)) {
                String warpName = args[1];
                NameStatus nameStatus = NameValidation.clean(player, warpName);
                if(nameStatus == NameStatus.VALID){
                    LocationStatus locationStatus = LocationValidation.canWarpPointBePlacedAt(player.getLocation());
                    if(locationStatus == LocationStatus.VALID){
                        Warp warp = new Warp(warpName, player.getLocation());
                        warpManager.registerWarp(player, warp);
                        player.sendMessage(SUCCESSFUL_CLAIM_MESSAGE(warp.getName()));
                    }else{
                        Notifications.sendWarning(player, locationStatus.getMessage());
                    }
                }else{
                    Notifications.sendWarning(player, nameStatus.getMessage());
                }
            }else{
                Notifications.sendWarning(player, WARP_LIMIT_ERROR);
                Notifications.sendTip(player, "warp del [warp-name]", "to remove a warp");
                Notifications.sendTip(player,"warp list", "to view all your warps");
            }
        }else {
            Notifications.sendFormat(player, "warp set [warp-name]");
        }


    }

    private static final String WARP_LIMIT_ERROR = "Warp limit reached";

    private static final String SUCCESSFUL_CLAIM_MESSAGE(String warpName){
        return (WarpMagic.themeLight + "Warp "
                + WarpMagic.themeDark + warpName
                + WarpMagic.themeLight + " set"
        );
    }
}
