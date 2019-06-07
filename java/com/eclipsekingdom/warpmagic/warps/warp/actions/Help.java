package com.eclipsekingdom.warpmagic.warps.warp.actions;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import com.eclipsekingdom.warpmagic.util.communication.PluginHelp;
import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.warps.warp.CommandWarp;
import com.eclipsekingdom.warpmagic.warps.warp.data.WarpManager;
import com.eclipsekingdom.warpmagic.warps.warp.data.WarpNumManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Help extends CommandAction {
    @Override
    public void run(Player player, String[] args) {
        PluginHelp.show(player, HELP_TITLE(player), CommandWarp.getInstance().getInfoList());
    }

    @Override
    protected CommandInfo initCommandInfo() {
        return new CommandInfo("warp help", "show this message");
    }

    @Override
    protected String initID() {
        return "help";
    }

    private static final String HELP_TITLE(Player player) {
        int plotsUsed = warpManager.getUsedWarpCount(player);
        int plotsMax = warpNumManager.getUnlockedWarpNum(player);
        return ( WarpMagic.themeLight + "" + ChatColor.BOLD + "WarpMagic - Warps" + ChatColor.ITALIC + "" + WarpMagic.themeDark + " - your warps: (" + plotsUsed + "/" + plotsMax + ")" );
    }


    private static final WarpManager warpManager = WarpManager.getInstance();
    private static final WarpNumManager warpNumManager = WarpNumManager.getInstance();
}
