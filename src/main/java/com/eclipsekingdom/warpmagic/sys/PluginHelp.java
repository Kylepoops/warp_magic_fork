package com.eclipsekingdom.warpmagic.sys;

import com.eclipsekingdom.warpmagic.sys.lang.Message;
import com.eclipsekingdom.warpmagic.util.CommandInfo;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class PluginHelp {

    public static void show(Player player, String title, List<CommandInfo> commandInfoList) {

        player.sendMessage(title);
        player.sendMessage(ChatColor.YELLOW + "-------" + ChatColor.GOLD + " " + Message.LABEL_COMMANDS + " " + ChatColor.YELLOW + "-------");

        for (CommandInfo commandInfo : commandInfoList) {
            String cmdComponent = ChatColor.GOLD + "/" + commandInfo.getCommand();
            cmdComponent = cmdComponent.replace("[", ChatColor.RED + "[");
            cmdComponent = cmdComponent.replace("]", "]" + ChatColor.GOLD);

            String descriptComponent = ChatColor.RESET + commandInfo.getDescription();
            descriptComponent = descriptComponent.replace("[", ChatColor.RED + "[");
            descriptComponent = descriptComponent.replace("]", "]" + ChatColor.RESET);

            player.sendMessage(cmdComponent + ": " + descriptComponent);
        }
    }

    public static void showSubList(Player player, List<CommandInfo> commandInfoList) {

        player.sendMessage(ChatColor.GOLD + "- - - - - - -");

        for (CommandInfo commandInfo : commandInfoList) {
            String cmdComponent = ChatColor.GOLD + "/" + commandInfo.getCommand();
            cmdComponent = cmdComponent.replace("[", ChatColor.RED + "[");
            cmdComponent = cmdComponent.replace("]", "]" + ChatColor.GOLD);

            String descriptComponent = ChatColor.RESET + commandInfo.getDescription();
            descriptComponent = descriptComponent.replace("[", ChatColor.RED + "[");
            descriptComponent = descriptComponent.replace("]", "]" + ChatColor.RESET);

            player.sendMessage(cmdComponent + ": " + descriptComponent);
        }
    }

}
