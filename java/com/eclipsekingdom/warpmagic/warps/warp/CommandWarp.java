package com.eclipsekingdom.warpmagic.warps.warp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandWarp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {


        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                WarpRequest request = WarpRequest.DEFAULT;
                request.process(player, args);
            } else {
                WarpRequest request = WarpRequest.fromString(args[0]);
                request.process(player, args);
            }
        }


        return false;
    }

}
