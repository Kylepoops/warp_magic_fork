package com.eclipsekingdom.warpmagic.warps.vortex.actions;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import com.eclipsekingdom.warpmagic.util.communication.PluginHelp;
import com.eclipsekingdom.warpmagic.warps.vortex.CommandVortex;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Help extends CommandAction {
    @Override
    public void run(Player player, String[] args) {
        PluginHelp.show(player, HELP_TITLE, CommandVortex.getInstance().getInfoList());
    }

    @Override
    protected CommandInfo initCommandInfo() {
        return new CommandInfo("vortex help", "show this message");
    }

    @Override
    protected String initID() {
        return "help";
    }

    private static final String HELP_TITLE = WarpMagic.themeLight + "" + ChatColor.BOLD + "WarpMagic - Vortexes";

}