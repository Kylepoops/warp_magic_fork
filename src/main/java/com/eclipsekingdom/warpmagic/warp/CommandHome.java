package com.eclipsekingdom.warpmagic.warp;

import com.eclipsekingdom.warpmagic.data.UserCache;
import com.eclipsekingdom.warpmagic.data.UserData;
import com.eclipsekingdom.warpmagic.sys.lang.Message;
import com.eclipsekingdom.warpmagic.util.CommandInfo;
import com.eclipsekingdom.warpmagic.util.Friend;
import com.eclipsekingdom.warpmagic.sys.PluginHelp;
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

        if (sender instanceof Player) {

            Player player = (Player) sender;

            if (args.length > 0) {
                String subCommand = args[0];
                switch (subCommand.toLowerCase()) {
                    case "set":
                        processSet(player);
                        break;
                    case "del":
                        processDel(player);
                        break;
                    case "invite":
                        processInvite(player, args);
                        break;
                    case "uninvite":
                        processUninvite(player, args);
                        break;
                    case "flist":
                        processFList(player);
                        break;
                    case "fclear":
                        processFClear(player);
                        break;
                    default:
                        processHelp(player);
                        break;
                }
            } else {
                processTeleport(player);
            }

        }

        return false;
    }

    private void processTeleport(Player player) {
        UserData userData = UserCache.getData(player);
        if (userData.hasHome()) {
            Teleportation.sendTo(player, userData.getHome().getLocation());
        } else {
            player.sendMessage(ChatColor.RED + Message.WARN_HOME_UNSET.toString());
        }
    }

    private void processSet(Player player) {
        UserData userData = UserCache.getData(player);
        if (userData.hasHome()) {
            Home home = userData.getHome();
            home.setLocation(player.getLocation());
            player.sendMessage(ChatColor.GREEN + Message.SUCCESS_HOME_UPDATE.toString());
        } else {
            userData.setHome(new Home(player.getLocation()));
            player.sendMessage(ChatColor.GREEN + Message.SUCCESS_HOME_SET.toString());
        }
    }

    private void processDel(Player player) {
        UserData userData = UserCache.getData(player);
        if (userData.hasHome()) {
            Home home = userData.getHome();
            List<Friend> friends = home.getFriends();
            for (int i = friends.size() - 1; i >= 0; i--) {
                processRemFriend(player, home, friends.get(i));
            }
            userData.setHome(null);
            player.sendMessage(ChatColor.GREEN + Message.SUCCESS_HOME_DELETE.toString());
        } else {
            player.sendMessage(ChatColor.RED + Message.WARN_HOME_UNSET.toString());
        }
    }

    private void processInvite(Player player, String[] args) {
        if (args.length > 0) {
            UserData userData = UserCache.getData(player);
            if (userData.hasHome()) {
                String targetName = args[1];
                Friend friend = Friend.from(targetName);
                if (friend != null) {
                    if (!friend.getName().equalsIgnoreCase(player.getName())) {
                        Home home = userData.getHome();
                        if (!home.isFriend(friend)) {
                            processAddFriend(player, home, friend);
                        } else {
                            player.sendMessage(ChatColor.RED + Message.WARN_ALREADY_FRIEND.fromPlayer(friend.getName()));
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + Message.WARN_ADD_SELF.toString());
                    }
                } else {
                    player.sendMessage(ChatColor.RED + Message.WARN_PLAYER_NOT_FOUND.fromPlayer(targetName));
                }
            } else {
                player.sendMessage(ChatColor.RED + Message.WARN_HOME_UNSET.toString());
            }
        } else {
            player.sendMessage(ChatColor.RED + Message.MISC_FORMAT.fromFormat("/home invite [" + Message.ARG_PLAYER + "]"));
        }
    }

    private void processAddFriend(Player player, Home home, Friend friend) {
        home.addFriend(friend);
        UserData friendData = UserCache.getData(friend.getID());
        Friend inviter = new Friend(player.getUniqueId(), player.getName());
        if (friendData != null) {
            friendData.addFriend(inviter);
        } else {
            UserCache.cache(friend.getID());
            UserCache.getData(friend.getID()).addFriend(inviter);
            UserCache.forget(friend.getID());
        }
        player.sendMessage(ChatColor.GREEN + Message.SUCCESS_HOME_INVITE.fromPlayer(friend.getName()));
        if (friend.isOnline()) {
            Player friendPlayer = friend.getPlayer();
            friendPlayer.sendMessage(ChatColor.GREEN + Message.SUCCESS_HOME_INVITATION.fromPlayer(player.getName()));
        }
    }

    private void processUninvite(Player player, String[] args) {
        if (args.length > 1) {
            UserData userData = UserCache.getData(player);
            if (userData.hasHome()) {
                String targetName = args[1];
                if (!targetName.equalsIgnoreCase(player.getName())) {
                    Home home = userData.getHome();
                    if (home.isFriend(targetName)) {
                        Friend friend = home.getFriend(targetName);
                        processRemFriend(player, home, friend);
                        player.sendMessage(ChatColor.GREEN + Message.SUCCESS_UNINVITE.fromPlayer(friend.getName()));
                    } else {
                        player.sendMessage(ChatColor.RED + Message.WARN_NOT_FRIEND.fromPlayer(targetName));
                    }
                } else {
                    player.sendMessage(ChatColor.RED + Message.WARN_REMOVE_SELF.toString());
                }
            } else {
                player.sendMessage(ChatColor.RED + Message.WARN_HOME_UNSET.toString());
            }
        } else {
            player.sendMessage(ChatColor.RED + Message.MISC_FORMAT.fromFormat("/home uninvite [" + Message.ARG_PLAYER + "]"));
        }
    }

    private void processRemFriend(Player player, Home home, Friend friend) {
        home.remFriend(friend.getName());
        UserData friendData = UserCache.getData(friend.getID());
        if (friendData != null) {
            friendData.removeFriend(player.getUniqueId());
        } else {
            UserCache.cache(friend.getID());
            friendData = UserCache.getData(friend.getID());
            friendData.removeFriend(player.getUniqueId());
            UserCache.forget(friend.getID());
        }
    }

    private void processFList(Player player) {
        UserData userData = UserCache.getData(player);
        if (userData.hasHome()) {
            player.sendMessage(ChatColor.GREEN + Message.LABEL_FRIENDS.toString() + ":");
            player.sendMessage(getFriendsAsString(userData.getHome()));
        } else {
            player.sendMessage(ChatColor.RED + Message.WARN_HOME_UNSET.toString());
        }
    }

    private String getFriendsAsString(Home home) {
        String friends = "";
        for (Friend friend : home.getFriends()) {
            if (friend.isOnline()) {
                friends += (", " + ChatColor.AQUA + friend.getName());
            } else {
                friends += (", " + ChatColor.GRAY + friend.getName());
            }
        }
        if (friends.length() > 2) {
            return friends.substring(2);
        } else {
            return "-";
        }
    }

    private void processFClear(Player player) {
        UserData userData = UserCache.getData(player);
        if (userData.hasHome()) {
            Home home = userData.getHome();
            List<Friend> friends = home.getFriends();
            for (int i = friends.size() - 1; i >= 0; i--) {
                processRemFriend(player, home, friends.get(i));
            }
            player.sendMessage(ChatColor.GREEN + Message.SUCCESS_FCLEAR.toString());

        } else {
            player.sendMessage(ChatColor.RED + Message.WARN_HOME_UNSET.toString());
        }
    }

    public static void processHelp(Player player) {
        PluginHelp.show(player, HELP_TITLE, homeInfoList);
        PluginHelp.showSubList(player, fhomeInfoList);
    }

    private static String HELP_TITLE = (ChatColor.GREEN + "" + ChatColor.BOLD + "Home");
    private static ImmutableList<CommandInfo> homeInfoList = new ImmutableList.Builder<CommandInfo>()
            .add(new CommandInfo("home", Message.HELP_TOHOME.toString()))
            .add(new CommandInfo("home set", Message.HELP_HOME_SET.toString()))
            .add(new CommandInfo("home del", Message.HELP_HOME_DEL.toString()))
            .add(new CommandInfo("home invite [" + Message.ARG_PLAYER + "]", Message.HELP_HOME_INVITE.toString()))
            .add(new CommandInfo("home uninvite [" + Message.ARG_PLAYER + "]", Message.HELP_HOME_UNINVITE.toString()))
            .add(new CommandInfo("home flist", Message.HELP_HOME_FLIST.toString()))
            .add(new CommandInfo("home fclear", Message.HELP_HOME_FCLEAR.toString()))
            .build();

    private static ImmutableList<CommandInfo> fhomeInfoList = new ImmutableList.Builder<CommandInfo>()
            .add(new CommandInfo("fhome [" + Message.ARG_PLAYER + "]", Message.HELP_TOFHOME.toString()))
            .add(new CommandInfo("fhome list", Message.HELP_FHOME_LIST.toString()))
            .build();

}
