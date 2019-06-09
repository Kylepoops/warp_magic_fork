package com.eclipsekingdom.warpmagic.util.commands.extras;

import com.eclipsekingdom.warpmagic.Permissions;
import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTop implements CommandExecutor {


    public CommandTop(WarpMagic plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(Permissions.hasExtras(player)){
                plugin.getTeleportation().sendTo(player, player.getWorld().getHighestBlockAt(player.getLocation()).getLocation().add(0.5,0,0.5));
            }else{
                Notifications.sendWarning(player, "You do not have permission for this command");
            }
        }
        return false;
    }

    private final WarpMagic plugin;

}
