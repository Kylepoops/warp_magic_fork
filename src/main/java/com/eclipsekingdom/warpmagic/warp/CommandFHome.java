package com.eclipsekingdom.warpmagic.warp;

import com.eclipsekingdom.warpmagic.data.UserCache;
import com.eclipsekingdom.warpmagic.data.UserData;
import com.eclipsekingdom.warpmagic.sys.lang.Message;
import com.eclipsekingdom.warpmagic.util.Amount;
import com.eclipsekingdom.warpmagic.util.Friend;
import com.eclipsekingdom.warpmagic.util.InfoList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandFHome implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length > 0) {
                String subCommand = args[0];
                switch (subCommand.toLowerCase()) {
                    case "help":
                        CommandHome.processHelp(player);
                        break;
                    case "list":
                        processList(player, args);
                        break;
                    default:
                        processTeleport(player, args);
                }
            } else {
                player.sendMessage(ChatColor.RED + Message.MISC_FORMAT.fromFormat("/fhome [" + Message.ARG_PLAYER + "]"));
            }
        }
        return false;
    }

    private void processTeleport(Player player, String[] args) {
        String targetName = args[0];
        UserData userData = UserCache.getData(player);
        Friend friend = userData.getFriend(targetName);
        if (friend != null) {
            if (!player.getName().equalsIgnoreCase(friend.getName())) {
                UserData friendData = UserCache.getData(friend.getID());
                if (friendData != null) {
                    if (friendData.hasHome()) {
                        Home home = friendData.getHome();
                        Teleportation.sendTo(player, home.getLocation());
                    } else {
                        player.sendMessage(ChatColor.RED + Message.WARN_FRIEND_HOME_UNSET.fromPlayer(friend.getName()));
                    }
                } else {
                    player.sendMessage(ChatColor.RED + Message.WARN_FRIEND_HOME_UNSET.fromPlayer(friend.getName()));
                }
            } else {
                player.sendMessage(ChatColor.RED + Message.WARN_NOT_HOME.toString());
            }
        } else {
            player.sendMessage(ChatColor.RED + Message.WARN_NOT_INVITED.fromPlayer(targetName));
        }
    }

    private static String LIST_TITLE = ChatColor.GREEN + "" + Message.LABEL_FRIEND_HOMES + ":";

    private void processList(Player player, String[] args) {
        List<String> items = new ArrayList<>();
        UserData userData = UserCache.getData(player);
        for (Friend friend : userData.getFriends()) {
            items.add(ChatColor.DARK_GREEN + friend.getName());
        }
        InfoList infoList = new InfoList(LIST_TITLE, items, 7);
        int page = args.length > 1 ? Amount.parse(args[1]) : 1;
        infoList.displayTo(player, page);
    }

}
