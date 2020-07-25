package com.eclipsekingdom.warpmagic.warp.requests;

import com.eclipsekingdom.warpmagic.sys.lang.Message;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class CommandTPDeny implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            Player target = (Player) sender;

            Request request = LiveRequests.getRequest(target);
            if (request != null) {
                LiveRequests.resolveRequest(target);
                target.sendMessage(ChatColor.GREEN + Message.SUCCESS_DENY_TPA.toString());
            } else  {
                target.sendMessage(ChatColor.RED + Message.WARN_NO_PENDING.toString());
            }
        }

        return false;
    }

}
