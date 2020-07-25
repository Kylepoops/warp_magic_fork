package com.eclipsekingdom.warpmagic.warp.global;

import com.eclipsekingdom.warpmagic.data.GlobalCache;
import com.eclipsekingdom.warpmagic.sys.Permissions;
import com.eclipsekingdom.warpmagic.sys.PluginBase;
import com.eclipsekingdom.warpmagic.sys.lang.Message;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetPoint implements CommandExecutor {

    private GlobalPoint point;
    private String formattedName;

    public CommandSetPoint(GlobalPoint point) {
        this.point = point;
        this.formattedName = point.toString().charAt(0) + point.toString().substring(1).toLowerCase();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (Permissions.canSetGlobalPoints(player)) {
                GlobalCache.set(point, player.getLocation());
                player.sendMessage(ChatColor.GREEN + Message.SUCCESS_GLOBAL_SET.fromWarp(formattedName));
                if (PluginBase.isUsingDynmap()) PluginBase.getDynmap().setGlobalIconAt(point, player.getLocation());
            } else {
                player.sendMessage(ChatColor.RED + Message.WARN_NOT_ALLOWED.toString());
            }
        }

        return false;
    }

}
