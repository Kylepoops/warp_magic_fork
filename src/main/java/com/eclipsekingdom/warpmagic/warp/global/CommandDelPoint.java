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

public class CommandDelPoint implements CommandExecutor {

    private GlobalPoint point;
    private String formattedName;

    public CommandDelPoint(GlobalPoint point) {
        this.point = point;
        this.formattedName = point.toString().charAt(0) + point.toString().substring(1).toLowerCase();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (Permissions.canSetGlobalPoints(player)) {
                GlobalCache.delete(point);
                if (PluginBase.isUsingDynmap()) PluginBase.getDynmap().removeGlobalIcon(point);
                player.sendMessage(ChatColor.GREEN + Message.SUCCESS_GLOBAL_DEL.fromWarp(formattedName));
            } else {
                player.sendMessage(ChatColor.RED + Message.WARN_NOT_ALLOWED.toString());
            }
        }

        return false;
    }

}
