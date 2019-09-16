package com.eclipsekingdom.warpmagic.warp.extras;

import com.eclipsekingdom.warpmagic.Permissions;
import com.eclipsekingdom.warpmagic.warp.Teleportation;
import com.eclipsekingdom.warpmagic.util.language.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTop implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(Permissions.hasExtras(player)){
                Teleportation.sendTo(player, player.getWorld().getHighestBlockAt(player.getLocation()).getLocation().add(0.5,0,0.5));
            }else{
                player.sendMessage(Message.ERROR_NOT_ALLOWED.get());
            }
        }
        return false;
    }
}
