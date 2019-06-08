package com.eclipsekingdom.warpmagic.warps.home.actions;

import com.eclipsekingdom.warpmagic.util.commands.CommandAction;
import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.warps.home.Home;
import com.eclipsekingdom.warpmagic.warps.home.HomeManager;
import com.eclipsekingdom.warpmagic.warps.home.RelationsManager;
import org.bukkit.entity.Player;

import java.util.List;

public class FClear extends CommandAction {

    @Override
    public void run(Player player, String[] args) {
        Home home = homeManager.getHome(player);
        if(home != null) {
            List<String> friends = home.getFriends();
            for(int i = friends.size()-1; i >=0; i--){
                String friendName = friends.get(i);
                home.remFriend(friendName);
                relationsManager.registerFriendRemove(player.getDisplayName(), friendName);
                homeManager.trackUnsavedData(player.getDisplayName());
            }
            homeManager.trackUnsavedData(player.getDisplayName());
            player.sendMessage(SUCCESSFUL_CLEAR_MESSAGE);

        }else{
            Notifications.sendWarning(player, HOME_UNSET_ERROR);
        }
    }


    private static final String HOME_UNSET_ERROR = "home not set";
    private static final String SUCCESSFUL_CLEAR_MESSAGE = "home not set";

    private final HomeManager homeManager = HomeManager.getInstance();
    private final RelationsManager relationsManager = RelationsManager.getInstance();

    @Override
    protected CommandInfo initCommandInfo() {
        return new CommandInfo("home fclear", "clear home friend list");
    }

    @Override
    protected String initID() {
        return "fclear";
    }
}
