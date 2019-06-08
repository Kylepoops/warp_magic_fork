package com.eclipsekingdom.warpmagic.warps.home.actions;

import com.eclipsekingdom.warpmagic.Teleportation;
import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.warps.home.Home;
import com.eclipsekingdom.warpmagic.warps.home.HomeManager;
import org.bukkit.entity.Player;

public class Default extends CommandAction {

    @Override
    public void run(Player player, String[] args) {
        Home home = homeManager.getHome(player);
        if(home != null) {
            Teleportation.sendTo(player, home.getLocation());
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
}