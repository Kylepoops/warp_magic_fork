package com.eclipsekingdom.warpmagic.warps.home.hactions;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.warps.home.Home;
import com.eclipsekingdom.warpmagic.warps.home.HomeManager;
import com.eclipsekingdom.warpmagic.warps.home.RelationsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class H_Invite extends CommandAction {
    @Override
    public void run(Player player, String[] args) {
        if(args.length > 1){
            Home home = homeManager.getHome(player);
            if(home != null) {
                String targetName = args[1];
                if(!targetName.equalsIgnoreCase(player.getDisplayName())){
                    Player friend = getPlayer(targetName);
                    if(friend != null){
                        if(processFriendRequest(player,home, friend.getDisplayName())){ //returns true if process went through
                            friend.sendMessage(INVITATION_MESSAGE(player.getDisplayName()));
                            Notifications.sendTip(friend, "fhome " + player.getDisplayName(), "to teleport there");
                        }
                    }else{
                        OfflinePlayer oFriend = getOfflinePlayer(targetName);
                        if(oFriend != null){
                            processFriendRequest(player,home, oFriend.getName());
                        }else{
                            Notifications.sendNotFound(player, "Player", targetName);
                        }
                    }
                }else{
                    Notifications.sendWarning(player, ADD_SELF_ERROR);
                }
            }else{
                Notifications.sendWarning(player, HOME_UNSET_ERROR);
            }
        }else{
            Notifications.sendFormat(player, "home invite [player-name]");
        }
    }

    @Override
    protected CommandInfo initCommandInfo() {
        return new CommandInfo("home invite [player-name]", "invite player to home");
    }

    @Override
    protected String initID() {
        return "invite";
    }

    private static final String HOME_UNSET_ERROR = "home not set";
    private static final String ADD_SELF_ERROR = "You can't invite yourself!";
    private static final String ALREADY_FRIEND_ERROR(String friendName){
        return (ChatColor.GRAY + friendName
                + ChatColor.RED + " is already invited to your home"
        );
    }
    private static final String SUCCESSFUL_INVITE_MESSAGE(String friendName){
        return (WarpMagic.themeDark + friendName
                + WarpMagic.themeLight + " was invited to your home"
        );
    }
    private static final String INVITATION_MESSAGE(String hostName){
        return (WarpMagic.themeLight + "You have been invited to "
                + WarpMagic.themeDark + hostName
                + WarpMagic.themeLight + "'s home"
        );
    }

    private final HomeManager homeManager = HomeManager.getInstance();
    private final RelationsManager relationsManager = RelationsManager.getInstance();


    private Player getPlayer(String name){
        return Bukkit.getPlayer(name);
    }

    private OfflinePlayer getOfflinePlayer(String name){
        OfflinePlayer oPlayer = null;
        for(OfflinePlayer op: Bukkit.getOfflinePlayers()){
            if(op.getName().equalsIgnoreCase(name)){
                oPlayer = op;
            }
        }
        return oPlayer;
    }

    private boolean processFriendRequest(Player player, Home home, String friendName){
        if(!home.getFriends().contains(friendName)){
            home.addFriend(friendName);
            relationsManager.registerFriendAdd(player.getDisplayName(), friendName);
            homeManager.trackUnsavedData(player.getDisplayName());
            player.sendMessage(SUCCESSFUL_INVITE_MESSAGE(friendName));
            return true;
        }else{
            Notifications.sendWarning(player, ALREADY_FRIEND_ERROR(friendName));
            return false;
        }
    }


}
