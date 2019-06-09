package com.eclipsekingdom.warpmagic.warps.home.hactions;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.warps.home.Home;
import com.eclipsekingdom.warpmagic.warps.home.HomeManager;
import org.bukkit.entity.Player;

public class H_Default extends CommandAction {

    public H_Default(WarpMagic plugin){
        this.plugin = plugin;
    }

    @Override
    public void run(Player player, String[] args) {
        Home home = homeManager.getHome(player);
        if(home != null) {
            plugin.getTeleportation().sendTo(player, home.getLocation());
        }else{
            Notifications.sendWarning(player, HOME_UNSET_ERROR);
            Notifications.sendTip(player, "home set", "to set your home");
        }
    }

    @Override
    protected CommandInfo initCommandInfo() {
        return new CommandInfo("home", "teleport home");
    }

    @Override
    protected String initID() {
        return "";
    }

    private static final String HOME_UNSET_ERROR = "home not set";

    private final HomeManager homeManager = HomeManager.getInstance();
    private final WarpMagic plugin;
}
