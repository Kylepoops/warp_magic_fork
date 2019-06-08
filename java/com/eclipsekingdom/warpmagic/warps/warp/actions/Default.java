package com.eclipsekingdom.warpmagic.warps.warp.actions;

import com.eclipsekingdom.warpmagic.Teleportation;
import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.warps.warp.Warp;
import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.warps.warp.WarpManager;
import org.bukkit.entity.Player;

public class Default extends CommandAction {

    @Override
    public void run(Player player, String[] args) {
        if(args.length > 0){
            String warpName = args[0];
            Warp warp = warpManager.getWarp(player, warpName);
            if(warp != null) {
                Teleportation.sendTo(player, warp.getLocation());
            }else{
                Notifications.sendNotFound(player, "Warp", warpName);
            }
        }else {
            Notifications.sendFormat(player, "warp [warp-name]");
        }
    }

    @Override
    protected CommandInfo initCommandInfo() {
        return new CommandInfo("warp [warp-name]", "teleport to [warp-name]");
    }

    @Override
    protected String initID() {
        return "";
    }

    private final WarpManager warpManager = WarpManager.getInstance();
}
