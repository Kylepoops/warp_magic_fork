package com.eclipsekingdom.warpmagic.warp.global;

import com.eclipsekingdom.warpmagic.warp.Teleportation;
import com.eclipsekingdom.warpmagic.data.GlobalCache;
import com.eclipsekingdom.warpmagic.util.language.Message;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandToPoint implements CommandExecutor {

    private GlobalPoint point;
    private String formattedName;

    public CommandToPoint(GlobalPoint point) {
        this.point = point;
        this.formattedName = point.toString().charAt(0) + point.toString().substring(1).toLowerCase();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location location = GlobalCache.get(point);
            if (location != null) {
                Teleportation.sendTo(player, location);
            } else {
                player.sendMessage(Message.ERROR_GLOBAL_NOT_SET.getFromWarp(formattedName));
            }
        }

        return false;
    }

}
