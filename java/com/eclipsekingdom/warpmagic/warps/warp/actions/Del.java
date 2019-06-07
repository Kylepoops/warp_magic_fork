package com.eclipsekingdom.warpmagic.warps.warp.actions;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.warps.warp.Warp;
import com.eclipsekingdom.warpmagic.warps.warp.WarpAction;
import org.bukkit.entity.Player;

public class Del extends WarpAction {

    @Override
    public void run(Player player, String[] args) {
        if(args.length > 1){
            String warpName = args[1];
            Warp warp = warpManager.getWarp(player, warpName);
            if(warp != null) {
                warpManager.removeWarp(player, warp);
                player.sendMessage(SUCCESSFUL_DELETE_MESSAGE(warp.getName()));
            }else{
                Notifications.sendNotFound(player, "Warp", warpName);
            }
        }else {
            Notifications.sendFormat(player, "warp del [warp-name]");
        }
    }

    private static final String SUCCESSFUL_DELETE_MESSAGE(String warpName){
        return (WarpMagic.themeLight + "Warp "
                + WarpMagic.themeDark + warpName
                + WarpMagic.themeLight + " was deleted"
        );
    }

}
