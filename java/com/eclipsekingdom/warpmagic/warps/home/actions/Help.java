package com.eclipsekingdom.warpmagic.warps.home.actions;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import com.eclipsekingdom.warpmagic.util.communication.PluginHelp;
import com.eclipsekingdom.warpmagic.warps.home.CommandFHome;
import com.eclipsekingdom.warpmagic.warps.home.CommandHome;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Help extends CommandAction {
    @Override
    public void run(Player player, String[] args) {
        PluginHelp.show(player, HELP_TITLE(player), CommandHome.getInstance().getInfoList());
        PluginHelp.showSubList(player, CommandFHome.getInstance().getInfoList());
    }

    @Override
    protected CommandInfo initCommandInfo() {
        return new CommandInfo("home help", "show this message");
    }

    @Override
    protected String initID() {
        return "help";
    }

    private static final String HELP_TITLE(Player player) {
        return ( WarpMagic.themeLight + "" + ChatColor.BOLD + "WarpMagic - Home" );
    }
}
