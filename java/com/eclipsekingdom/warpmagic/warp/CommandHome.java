package com.eclipsekingdom.warpmagic.warp;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.data.UserCache;
import com.eclipsekingdom.warpmagic.data.UserData;
import com.eclipsekingdom.warpmagic.util.CommandInfo;
import com.eclipsekingdom.warpmagic.util.PluginHelp;
import com.eclipsekingdom.warpmagic.util.language.Message;
import com.eclipsekingdom.warpmagic.warp.validation.LocationValidation;
import com.eclipsekingdom.warpmagic.warp.validation.LocationStatus;
import com.google.common.collect.ImmutableList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandHome implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            Player player = (Player) sender;

            if(args.length > 0){
                String subCommand = args[0];
                switch (subCommand.toLowerCase()){
                    case "help": processHelp(player); break;
                    case "set": processSet(player); break;
                    case "del": processDel(player); break;
                    case "invite": processInvite(player, args); break;
                    case "uninvite": processUninvite(player, args); break;
                    case "flist": processFList(player); break;
                    case "fclear": processFClear(player); break;
                    default: processHelp(player); break;
                }
            }else{
                processTeleport(player);
            }

        }

        return false;
    }

    private void processTeleport(Player player){
        UserData userData = UserCache.getData(player);
        if(userData.hasHome()){
            Teleportation.sendTo(player, userData.getHome().getLocation());
        }else{
            player.sendMessage(Message.HOME_UNSET.get());
            player.sendMessage(Message.SUGGEST_SET_HOME.get());
        }
    }

    private void processSet(Player player){
        LocationStatus status = LocationValidation.canWarpPointBePlacedAt(player.getLocation());
        if(status == LocationStatus.VALID){
            UserData userData = UserCache.getData(player);
            if(userData.hasHome()){
                Home home = userData.getHome();
                home.setLocation(player.getLocation());
                player.sendMessage(Message.HOME_UPDATE.get());
            }else{
                userData.setHome(new Home(player.getLocation()));
                player.sendMessage(Message.HOME_SET.get());
            }
        }else{
            player.sendMessage(status.message);
        }
    }

    private void processDel(Player player){
        UserData userData = UserCache.getData(player);
        if(userData.hasHome()){
            Home home = userData.getHome();
            List<Friend> friends = home.getFriends();
            for(int i = friends.size()-1; i>=0;i--){
                processRemFriend(player, home, friends.get(i));
            }
            userData.setHome(null);
            player.sendMessage(Message.HOME_DELETE.get());
        }else{
            player.sendMessage(Message.HOME_UNSET.get());
        }
    }

    private void processInvite(Player player, String[] args){
        if(args.length > 0){
            UserData userData = UserCache.getData(player);
            if(userData.hasHome()){
                String targetName = args[1];
                Friend friend = Friend.from(targetName);
                if(friend != null){
                    if(!friend.getName().equalsIgnoreCase(player.getName())){
                        Home home = userData.getHome();
                        if(!home.isFriend(friend)){
                            processAddFriend(player, home, friend);
                        }else{
                            player.sendMessage(Message.ERROR_ALREADY_FRIEND.getFromPlayer(friend.getName()));
                        }
                    }else{
                        player.sendMessage(Message.ERROR_ADD_SELF.get());
                    }
                }else{
                    player.sendMessage(Message.ERROR_PLAYER_NOT_FOUND.getFromPlayer(targetName));
                }
            }else{
                player.sendMessage(Message.HOME_UNSET.get());
            }
        }else{
            player.sendMessage(Message.FORMAT_HOME_INVITE.get());
        }
    }

    private void processAddFriend(Player player, Home home, Friend friend){
        home.addFriend(friend);
        UserData friendData = UserCache.getData(friend.getID());
        Friend inviter = new Friend(player.getUniqueId(), player.getName());
        if(friendData != null){
            friendData.addFriend(inviter);
        }else{
            UserCache.cache(friend.getID());
            UserCache.getData(friend.getID()).addFriend(inviter);
            UserCache.forget(friend.getID());
        }
        player.sendMessage(Message.HOME_INVITE.getFromPlayer(friend.getName()));
        if(friend.isOnline()){
            Player friendPlayer = friend.getPlayer();
            friendPlayer.sendMessage(Message.HOME_INVITATION.getFromPlayer(player.getName()));
            friendPlayer.sendMessage(Message.SUGGEST_FHOME.getFromPlayer(player.getName()));
        }
    }

    private void processUninvite(Player player, String[] args){
        if(args.length > 1){
            UserData userData = UserCache.getData(player);
            if(userData.hasHome()){
                String targetName = args[1];
                if(!targetName.equalsIgnoreCase(player.getName())){
                    Home home = userData.getHome();
                    if(home.isFriend(targetName)){
                        Friend friend = home.getFriend(targetName);
                        processRemFriend(player, home, friend);
                        player.sendMessage(Message.SUCCESS_UNINVITE.getFromPlayer(friend.getName()));
                    }else{
                        player.sendMessage(Message.ERROR_NOT_FRIEND.getFromPlayer(targetName));
                    }
                }else{
                    player.sendMessage(Message.ERROR_REMOVE_SELF.get());
                }
            }else{
                player.sendMessage(Message.HOME_UNSET.get());
            }
        }else{
            player.sendMessage(Message.FORMAT_HOME_UNINVITE.get());
        }
    }

    private void processRemFriend(Player player, Home home, Friend friend){
        home.remFriend(friend.getName());
        UserData friendData = UserCache.getData(friend.getID());
        if(friendData != null){
            friendData.removeFriend(player.getUniqueId());
        }else{
            UserCache.cache(friend.getID());
            friendData = UserCache.getData(friend.getID());
            friendData.removeFriend(player.getUniqueId());
            UserCache.forget(friend.getID());
        }
    }

    private void processFList(Player player){
        UserData userData = UserCache.getData(player);
        if(userData.hasHome()){
            player.sendMessage(Message.FRIEND_LIST_TITLE.get());
            player.sendMessage(getFriendsAsString(userData.getHome()));
        }else{
            player.sendMessage(Message.HOME_UNSET.get());
        }
    }

    private String getFriendsAsString(Home home){
        String friends = "";
        for(Friend friend: home.getFriends()){
            if(friend.isOnline()){
                friends += (", " + ChatColor.DARK_AQUA + friend.getName());
            }else{
                friends += (", " + ChatColor.GRAY + friend.getName());
            }
        }
        if(friends.length() > 2){
            return friends.substring(2);
        }else{
            return "-";
        }
    }

    private void processFClear(Player player){
        UserData userData = UserCache.getData(player);
        if(userData.hasHome()){
            Home home = userData.getHome();
            List<Friend> friends = home.getFriends();
            for(int i = friends.size()-1; i >=0; i--){
                processRemFriend(player, home, friends.get(i));
            }
            player.sendMessage(Message.SUCCESS_FCLEAR.get());

        }else{
            player.sendMessage(Message.HOME_UNSET.get());
        }
    }

    public static void processHelp(Player player){
        PluginHelp.show(player, HELP_TITLE, homeInfoList);
        PluginHelp.showSubList(player, fhomeInfoList);
    }

    private static String HELP_TITLE = (WarpMagic.themeLight + "" + ChatColor.BOLD + "WarpMagic - Home" );
    private static ImmutableList<CommandInfo> homeInfoList = new ImmutableList.Builder<CommandInfo>()
            .add(new CommandInfo("home", "teleport home"))
            .add(new CommandInfo("home help", "display home commands"))
            .add(new CommandInfo("home set", "set or update home location"))
            .add(new CommandInfo("home del", "delete home"))
            .add(new CommandInfo("home invite [player]", "invite player to home"))
            .add(new CommandInfo("home uninvite [player]", "uninvite player from home"))
            .add(new CommandInfo("home flist", "list home friends"))
            .add(new CommandInfo("home fclear", "clear home friends"))
            .build();

    private static ImmutableList<CommandInfo> fhomeInfoList = new ImmutableList.Builder<CommandInfo>()
            .add(new CommandInfo("fhome [player]", "teleport to [player]'s home"))
            .add(new CommandInfo("fhome list", "list homes you are invited to"))
            .build();


}
