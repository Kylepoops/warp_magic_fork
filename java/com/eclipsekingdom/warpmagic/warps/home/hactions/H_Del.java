package com.eclipsekingdom.warpmagic.warps.home.hactions;

import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.warps.home.Home;
import com.eclipsekingdom.warpmagic.warps.home.HomeManager;
import org.bukkit.entity.Player;

public class H_Del extends CommandAction {

    @Override
    public void run(Player player, String[] args) {
        Home home = homeManager.getHome(player);
        if(home != null) {
            homeManager.removeHome(player);
            Notifications.sendSuccess(player, SUCCESSFUL_DELETE_MESSAGE);
        }else{
            Notifications.sendWarning(player, HOME_UNSET_ERROR);
        }
    }

    @Override
    protected CommandInfo initCommandInfo() {
        return new CommandInfo("home del", "remove home");
    }

    @Override
    protected String initID() {
        return "del";
    }

    private static final String SUCCESSFUL_DELETE_MESSAGE = "home deleted";
    private static final String HOME_UNSET_ERROR = "home not set";

    private final HomeManager homeManager = HomeManager.getInstance();
}
