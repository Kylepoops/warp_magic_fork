package com.eclipsekingdom.warpmagic.util.commands.extras;

import com.eclipsekingdom.warpmagic.Permissions;
import com.eclipsekingdom.warpmagic.WarpMagic;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBottom implements CommandExecutor {

    public CommandBottom(WarpMagic plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(Permissions.hasExtras(player)){

                Location bottom = player.getLocation();
                int emptyBlocksInARow = 0;
                for(int i = 1; i<255;i++){
                    bottom.setY(i);
                    if(bottom.getBlock().isPassable()){
                        emptyBlocksInARow++;
                    }else{
                        emptyBlocksInARow=0;
                    }
                    if(emptyBlocksInARow == 2){
                        break;
                    }
                }
                bottom.setY(bottom.getY() - 1);
                plugin.getTeleportation().sendTo(player, bottom);
            }else{
                Notifications.sendWarning(player, "You do not have permission for this command");
            }
        }
        return false;
    }


    private final WarpMagic plugin;

}
