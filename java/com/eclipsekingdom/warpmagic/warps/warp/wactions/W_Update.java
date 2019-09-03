package com.eclipsekingdom.warpmagic.warps.warp.wactions;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.warps.LocationValidation;
import com.eclipsekingdom.warpmagic.warps.warp.Warp;
import com.eclipsekingdom.warpmagic.warps.warp.WarpManager;
import org.bukkit.entity.Player;

public class W_Update extends CommandAction {

    private final WarpManager warpManager = WarpManager.getInstance();

    @Override
    public void run(Player player, String[] args) {

        if(args.length > 1){
            String warpName = args[1];
            if(warpAlreadySet(player, warpName)){
                LocationValidation.Status locationStatus = LocationValidation.canWarpPointBePlacedAt(player.getLocation());
                if(locationStatus == LocationValidation.Status.VALID){
                    Warp warp = warpManager.getWarp(player, warpName);
                    warp.updateLocation(player.getLocation());
                    warpManager.trackUnsavedData(player.getUniqueId());
                    player.sendMessage(SUCCESSFUL_UPDATE_MESSAGE(warp.getName()));
                }else{
                    Notifications.sendWarning(player, locationStatus.message);
                }
            }else{
                Notifications.sendWarning(player, NOT_FOUND_MESSAGE(warpName));
            }
        }else {
            Notifications.sendFormat(player, "warp update [warp-name]");
        }


    }

    @Override
    protected CommandInfo initCommandInfo() {
        return new CommandInfo("warp update [warp-name]", "update warp at location");
    }

    @Override
    protected String initID() {
        return "update";
    }


    private static final String NOT_FOUND_MESSAGE(String warpName){
        return (warpName + " not found");
    }

    private static final String SUCCESSFUL_UPDATE_MESSAGE(String warpName){
        return (WarpMagic.themeLight + "Warp "
                + WarpMagic.themeDark + warpName
                + WarpMagic.themeLight + " updated"
        );
    }

    private boolean warpAlreadySet(Player player, String name){
        for(Warp warp: warpManager.getWarps(player)){
            if(warp.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }


}
