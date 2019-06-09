package com.eclipsekingdom.warpmagic.warps.warp.wactions;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.warps.warp.Warp;
import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.warps.warp.WarpManager;
import org.bukkit.entity.Player;

public class W_Del extends CommandAction {

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

    @Override
    protected CommandInfo initCommandInfo() {
        return new CommandInfo("warp del [warp-name]", "remove warp");
    }

    @Override
    protected String initID() {
        return "del";
    }

    private static final String SUCCESSFUL_DELETE_MESSAGE(String warpName){
        return (WarpMagic.themeLight + "Warp "
                + WarpMagic.themeDark + warpName
                + WarpMagic.themeLight + " was deleted"
        );
    }


    private final WarpManager warpManager = WarpManager.getInstance();
}
