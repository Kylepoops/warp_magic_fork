package com.eclipsekingdom.warpmagic.warps.home.actions;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.warps.home.Home;
import com.eclipsekingdom.warpmagic.warps.home.HomeManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class FList extends CommandAction {
    @Override
    public void run(Player player, String[] args) {
        Home home = homeManager.getHome(player);
        if(home != null) {
            player.sendMessage(WarpMagic.themeLight + "Friends:");
            String friendList = getFriendsAsString(home);
            if(friendList.length()>1){
                player.sendMessage(ChatColor.DARK_AQUA + "*online");
                player.sendMessage(friendList);
            }else{
                player.sendMessage(friendList);
            }
        }else{
            Notifications.sendWarning(player, HOME_UNSET_ERROR);
        }
    }


    private static final String HOME_UNSET_ERROR = "home not set";

    private final HomeManager homeManager = HomeManager.getInstance();

    @Override
    protected CommandInfo initCommandInfo() {
        return new CommandInfo("home flist", "list friends of home");
    }

    @Override
    protected String initID() {
        return "flist";
    }

    private String getFriendsAsString(Home home){
        String friends = ChatColor.GRAY+"";
        for(String friendName: home.getFriends()){
            if(isOnline(friendName)){
                friends += (", " + ChatColor.DARK_AQUA + friendName);
            }else{
                friends += (", " + friendName);
            }
        }
        if(friends.length() > 4){
            return friends.substring(4);
        }else{
            return "-";
        }
    }

    private boolean isOnline(String playerName){
        return (Bukkit.getPlayer(playerName) != null);
    }
}
