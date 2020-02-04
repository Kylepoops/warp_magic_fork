package com.eclipsekingdom.warpmagic.warp;

import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.data.UserCache;
import com.eclipsekingdom.warpmagic.data.UserData;
import com.eclipsekingdom.warpmagic.util.Amount;
import com.eclipsekingdom.warpmagic.util.InfoList;
import com.eclipsekingdom.warpmagic.util.language.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandFHome implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(args.length > 0){
                String subCommand = args[0];
                switch (subCommand.toLowerCase()){
                    case "help": CommandHome.processHelp(player); break;
                    case "list": processList(player, args); break;
                    default: processTeleport(player, args);
                }
            }else{
                player.sendMessage(Message.FORMAT_FHOME.get());
            }
        }
        return false;
    }

    private void processTeleport(Player player, String[] args){
        String targetName = args[0];
        UserData userData = UserCache.getData(player);
        Friend friend = userData.getFriend(targetName);
        if(friend != null){
            if(!player.getName().equalsIgnoreCase(friend.getName())){
                UserData friendData = UserCache.getData(friend.getID());
                if(friendData != null){
                    if(friendData.hasHome()){
                        Home home = friendData.getHome();
                        Teleportation.sendTo(player, home.getLocation());
                    }else {
                        player.sendMessage(Message.ERROR_FRIEND_HOME_UNSET.getFromPlayer(friend.getName()));
                    }
                }else{
                    player.sendMessage(Message.ERROR_FRIEND_HOME_UNSET.getFromPlayer(friend.getName()));
                }
            }else{
                player.sendMessage(Message.ERROR_NOT_HOME.get());
                player.sendMessage(Message.SUGGEST_HOME.get());
            }
        }else{
            player.sendMessage(Message.ERROR_NOT_INVITED.getFromPlayer(targetName));
        }
    }

    private void processList(Player player, String[] args){
        List<String> items = new ArrayList<>();
        UserData userData = UserCache.getData(player);
        for(Friend friend: userData.getFriends()){
            items.add(WarpMagic.themeDark + friend.getName());
        }
        InfoList infoList = new InfoList(Message.FHOME_LIST_TITLE.get(),items, 7, "fhome list");
        int page = args.length > 1 ? Amount.parse(args[1]): 1;
        infoList.displayTo(player, page);
    }

}
