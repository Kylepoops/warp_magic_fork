package com.eclipsekingdom.warpmagic.warps.warp.actions;

import com.eclipsekingdom.warpmagic.Permissions;
import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.warps.LocationValidation;
import com.eclipsekingdom.warpmagic.warps.NameValidation;
import com.eclipsekingdom.warpmagic.warps.warp.Warp;
import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.warps.warp.WarpManager;
import com.eclipsekingdom.warpmagic.warps.warp.WarpNumManager;
import org.bukkit.entity.Player;

public class Set extends CommandAction {

    @Override
    public void run(Player player, String[] args) {

        if(args.length > 1){
            if((warpManager.getUsedWarpCount(player) < warpNumManager.getUnlockedWarpNum(player)) || Permissions.canBypassLimit(player)) {
                String warpName = args[1];
                NameValidation.Status nameStatus = NameValidation.clean(player, warpName);
                if(nameStatus == NameValidation.Status.VALID){
                    LocationValidation.Status locationStatus = LocationValidation.canWarpPointBePlacedAt(player.getLocation());
                    if(locationStatus == LocationValidation.Status.VALID){
                        if(!warpAlreadySet(player, warpName)){
                            Warp warp = new Warp(warpName, player.getLocation());
                            warpManager.registerWarp(player, warp);
                            player.sendMessage(SUCCESSFUL_CLAIM_MESSAGE(warp.getName()));
                        }else{
                            Warp warp = warpManager.getWarp(player, warpName);
                            warp.updateLocation(player.getLocation());
                            warpManager.trackUnsavedData(player.getUniqueId());
                            player.sendMessage(SUCCESSFUL_UPDATE_MESSAGE(warp.getName()));
                        }
                    }else{
                        Notifications.sendWarning(player, locationStatus.message);
                    }
                }else{
                    Notifications.sendWarning(player, nameStatus.message);
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

    @Override
    protected CommandInfo initCommandInfo() {
        return new CommandInfo("warp set [warp-name]", "set warp at location");
    }

    @Override
    protected String initID() {
        return "set";
    }

    private static final String WARP_LIMIT_ERROR = "Warp limit reached";

    private static final String SUCCESSFUL_CLAIM_MESSAGE(String warpName){
        return (WarpMagic.themeLight + "Warp "
                + WarpMagic.themeDark + warpName
                + WarpMagic.themeLight + " set"
        );
    }

    private static final String SUCCESSFUL_UPDATE_MESSAGE(String warpName){
        return (WarpMagic.themeLight + "Warp "
                + WarpMagic.themeDark + warpName
                + WarpMagic.themeLight + " updated"
        );
    }


    private final WarpNumManager warpNumManager = WarpNumManager.getInstance();
    private final WarpManager warpManager = WarpManager.getInstance();

    private boolean warpAlreadySet(Player player, String name){
        for(Warp warp: warpManager.getWarps(player)){
            if(warp.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

}
