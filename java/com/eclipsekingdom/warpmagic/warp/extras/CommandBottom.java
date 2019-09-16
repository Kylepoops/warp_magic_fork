package com.eclipsekingdom.warpmagic.warp.extras;

import com.eclipsekingdom.warpmagic.Permissions;
import com.eclipsekingdom.warpmagic.warp.Teleportation;
import com.eclipsekingdom.warpmagic.util.language.Message;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBottom implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(Permissions.hasExtras(player)){

                Location bottom = player.getLocation();
                boolean atLeastOneSolidBlock = false;
                int emptyBlocksInARow = 0;
                for(int i = 1; i<255;i++){
                    bottom.setY(i);
                    if(bottom.getBlock().isPassable()){
                        emptyBlocksInARow++;
                    }else{
                        emptyBlocksInARow=0;
                        atLeastOneSolidBlock = true;
                    }
                    if(emptyBlocksInARow == 2 && atLeastOneSolidBlock){
                        break;
                    }
                }
                bottom.setY(bottom.getY() - 1);
                Teleportation.sendTo(player, bottom);
            }else{
                player.sendMessage(Message.ERROR_NOT_ALLOWED.get());
            }
        }
        return false;
    }

}
