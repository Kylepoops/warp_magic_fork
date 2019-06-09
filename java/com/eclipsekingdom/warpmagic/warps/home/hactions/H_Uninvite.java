package com.eclipsekingdom.warpmagic.warps.home.hactions;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.warps.home.Home;
import com.eclipsekingdom.warpmagic.warps.home.HomeManager;
import com.eclipsekingdom.warpmagic.warps.home.RelationsManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class H_Uninvite extends CommandAction {
    @Override
    public void run(Player player, String[] args) {
        if(args.length > 1){
            Home home = homeManager.getHome(player);
            if(home != null) {
                String friendName = args[1];
                if(!friendName.equalsIgnoreCase(player.getDisplayName())){
                    if(home.trusts(friendName)){
                        home.remFriend(friendName);
                        relationsManager.registerFriendRemove(player.getDisplayName(), friendName);
                        homeManager.trackUnsavedData(player.getDisplayName());
                        player.sendMessage(SUCCESSFUL_UNINVITE_MESSAGE(friendName));
                    }else{
                        Notifications.sendWarning(player, NOT_FRIEND_ERROR(friendName));
                    }
                }else{
                    Notifications.sendWarning(player, ADD_SELF_ERROR);
                }
            }else{
                Notifications.sendWarning(player, HOME_UNSET_ERROR);
            }
        }else{
            Notifications.sendFormat(player, "home uninvite [player-name]");
        }
    }

    @Override
    protected CommandInfo initCommandInfo() {
        return new CommandInfo("home uninvite [player-name]", "uninvite player from home");
    }

    @Override
    protected String initID() {
        return "uninvite";
    }

    private static final String HOME_UNSET_ERROR = "home not set";
    private static final String ADD_SELF_ERROR = "You can't uninvite yourself!";
    private static final String NOT_FRIEND_ERROR(String friendName){
        return (ChatColor.GRAY + friendName
                + ChatColor.RED + " is already invited to your home"
        );
    }
    private static final String SUCCESSFUL_UNINVITE_MESSAGE(String friendName){
        return (WarpMagic.themeDark + friendName
                + WarpMagic.themeLight + " was uninvited from your home"
        );
    }

    private final HomeManager homeManager = HomeManager.getInstance();
    private final RelationsManager relationsManager = RelationsManager.getInstance();

}
