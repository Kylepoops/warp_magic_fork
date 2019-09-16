package com.eclipsekingdom.warpmagic.warp.global;

import com.eclipsekingdom.warpmagic.Permissions;
import com.eclipsekingdom.warpmagic.data.GlobalCache;
import com.eclipsekingdom.warpmagic.util.language.Message;
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
            if(Permissions.canSetGlobalPoints(player)){
                GlobalCache.set(point, player.getLocation());
                player.sendMessage(Message.SUCCESS_GLOBAL_SET.getFromWarp(formattedName));
            }else{
                player.sendMessage(Message.ERROR_NOT_ALLOWED.get());
            }
        }

        return false;
    }

}
