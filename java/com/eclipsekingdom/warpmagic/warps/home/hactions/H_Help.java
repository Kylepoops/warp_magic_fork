package com.eclipsekingdom.warpmagic.warps.home.hactions;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import com.eclipsekingdom.warpmagic.util.communication.PluginHelp;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class H_Help extends CommandAction {

    public H_Help(WarpMagic plugin){
        this.plugin = plugin;
    }

    @Override
    public void run(Player player, String[] args) {
        PluginHelp.show(player, HELP_TITLE, plugin.getPluginCommands().getCommandHome().getInfoList());
        PluginHelp.showSubList(player, plugin.getPluginCommands().getCommandFHome().getInfoList());
    }

    @Override
    protected CommandInfo initCommandInfo() {
        return new CommandInfo("home help", "show this message");
    }

    @Override
    protected String initID() {
        return "help";
    }

    private static final String HELP_TITLE = ( WarpMagic.themeLight + "" + ChatColor.BOLD + "WarpMagic - Home" );

    private final WarpMagic plugin;
}
