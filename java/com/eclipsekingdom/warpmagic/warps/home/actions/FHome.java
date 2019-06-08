package com.eclipsekingdom.warpmagic.warps.home.actions;

import com.eclipsekingdom.warpmagic.Teleportation;
import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.warps.home.Home;
import com.eclipsekingdom.warpmagic.warps.home.HomeManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class FHome extends CommandAction {
    @Override
    public void run(Player player, String[] args) {
        if(args.length > 0){
            String friendName = args[0];
            if(!player.getDisplayName().equalsIgnoreCase(friendName)){
                Home friendHome = homeManager.getHome(friendName);
                if(friendHome != null){
                    if(friendHome.trusts(player.getDisplayName())){
                        Teleportation.sendTo(player, friendHome.getLocation());
                    }else{
                        player.sendMessage(NOT_INVITED(friendName));
                    }
                }else{
                    player.sendMessage(NOT_INVITED(friendName));
                }
            }else{
                Notifications.sendWarning(player, NOT_FHOME_ERROR);
                Notifications.sendTip(player, "home", "to teleport home");
            }
        }else{
            Notifications.sendFormat(player, "fhome [friend-name]");
        }
    }

    @Override
    protected CommandInfo initCommandInfo() {
        return new CommandInfo("fhome [friend-name]", "teleport to [friend-name]'s home");
    }

    @Override
    protected String initID() {
        return "";
    }

    private static final String NOT_FHOME_ERROR = "This command is for teleporting to friend's homes";

    private static final String NOT_INVITED(String friendName){
        return (ChatColor.RED + "You are not invited to "
                + ChatColor.GRAY + friendName
                + ChatColor.RED + "'s home");
    }

    private final HomeManager homeManager = HomeManager.getInstance();

}
