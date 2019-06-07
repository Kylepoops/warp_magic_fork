package com.eclipsekingdom.warpmagic.warps.warp.actions;

import com.eclipsekingdom.warpmagic.teleport.TeleportAction;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.warps.warp.Warp;
import com.eclipsekingdom.warpmagic.warps.warp.WarpAction;
import org.bukkit.entity.Player;

public class Default extends WarpAction {
    @Override
    public void run(Player player, String[] args) {
        if(args.length > 0){
            String warpName = args[0];
            Warp warp = warpManager.getWarp(player, warpName);
            if(warp != null) {
                new TeleportAction(player, warp.getLocation());
            }else{
                Notifications.sendNotFound(player, "Warp", warpName);
            }
        }else {
            Notifications.sendFormat(player, "warp [warp-name]");
        }
    }
}
