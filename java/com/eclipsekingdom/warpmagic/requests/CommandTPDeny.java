package com.eclipsekingdom.warpmagic.requests;

import com.eclipsekingdom.warpmagic.util.communication.Notifications;
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
                Notifications.sendSuccess(target, SUCCESSFUL_DENY_MESSAGE);
            } else  {
                Notifications.sendWarning(target, NO_REQUEST_ERROR);
            }
        }

        return false;
    }

    private static final String SUCCESSFUL_DENY_MESSAGE = "request denied";
    private static final String NO_REQUEST_ERROR = "You do not have any pending teleport requests";

}
