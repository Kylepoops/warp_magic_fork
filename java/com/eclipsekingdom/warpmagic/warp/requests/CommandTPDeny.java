package com.eclipsekingdom.warpmagic.warp.requests;

import com.eclipsekingdom.warpmagic.util.language.Message;
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
                target.sendMessage(Message.SUCCESS_DENY_TPA.get());
            } else  {
                target.sendMessage(Message.ERROR_NO_PENDING.get());
            }
        }

        return false;
    }

}
