package com.eclipsekingdom.warpmagic.global;

import com.eclipsekingdom.warpmagic.Permissions;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetNorth implements CommandExecutor {

    private GlobalManager globalManager = GlobalManager.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if(Permissions.canSetGlobalPoints(player)){
                globalManager.setNorth(player.getLocation());
                Notifications.sendSuccess(player, "north set");
            }else{
                Notifications.sendWarning(player, "You do not have permission for this command");
            }
        }

        return false;
    }
}
