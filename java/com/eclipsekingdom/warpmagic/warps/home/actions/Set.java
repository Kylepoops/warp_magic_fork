package com.eclipsekingdom.warpmagic.warps.home.actions;

import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.warps.LocationValidation;
import com.eclipsekingdom.warpmagic.warps.home.Home;
import com.eclipsekingdom.warpmagic.warps.home.HomeManager;
import org.bukkit.entity.Player;

public class Set extends CommandAction {

    @Override
    public void run(Player player, String[] args) {

        LocationValidation.Status locationStatus = LocationValidation.canWarpPointBePlacedAt(player.getLocation());
        if(locationStatus == LocationValidation.Status.VALID){
            if(!homeAlreadySet(player)){
                Home home = new Home(player.getLocation());
                homeManager.registerHome(player, home);
                player.sendMessage(SUCCESSFUL_CLAIM_MESSAGE);
            }else{
                Home home = homeManager.getHome(player);
                home.updateLocation(player.getLocation());
                player.sendMessage(SUCCESSFUL_UPDATE_MESSAGE);
            }
        }else{
            Notifications.sendWarning(player, locationStatus.message);
        }

    }

    @Override
    protected CommandInfo initCommandInfo() {
        return new CommandInfo("home set", "set home at location");
    }

    @Override
    protected String initID() {
        return "set";
    }

    private static final String SUCCESSFUL_CLAIM_MESSAGE = "home set";

    private static final String SUCCESSFUL_UPDATE_MESSAGE = "home updated";


    private final HomeManager homeManager = HomeManager.getInstance();

    private boolean homeAlreadySet(Player player){
        Home home = homeManager.getHome(player);
        if(home != null){
            return true;
        }else{
            return false;
        }
    }

}
