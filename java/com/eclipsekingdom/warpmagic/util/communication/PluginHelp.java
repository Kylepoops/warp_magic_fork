package com.eclipsekingdom.warpmagic.util.communication;

import com.eclipsekingdom.warpmagic.util.commands.CommandInfo;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class PluginHelp {

    public static void show(Player player, String title, List<CommandInfo> commandInfoList){

        player.sendMessage(title);
        player.sendMessage(ChatColor.YELLOW + "-------" + ChatColor.GOLD + " Commands " + ChatColor.YELLOW + "-------");

        for(CommandInfo commandInfo: commandInfoList){
            String cmdComponent = ChatColor.GOLD + "/" + commandInfo.getCommand();
            cmdComponent = cmdComponent.replace("[", ChatColor.RED +"[");
            cmdComponent = cmdComponent.replace("]", "]" + ChatColor.GOLD);

            String descriptComponent = ChatColor.RESET + commandInfo.getDescription();
            descriptComponent= descriptComponent.replace("[", ChatColor.RED + "[");
            descriptComponent = descriptComponent.replace("]", "]" + ChatColor.RESET);

            player.sendMessage(cmdComponent + ": " + descriptComponent);
        }
    }


}
