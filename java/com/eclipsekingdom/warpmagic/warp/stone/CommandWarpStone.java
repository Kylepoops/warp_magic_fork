package com.eclipsekingdom.warpmagic.warp.stone;

import com.eclipsekingdom.warpmagic.Permissions;
import com.eclipsekingdom.warpmagic.communication.Notifications;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandWarpStone implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;
            if(Permissions.canSummonWarpStone(player)){
                int amt = 1;
                if(args.length > 0){
                    try{
                        amt = Integer.parseInt(args[0]);
                    }catch (Exception e){
                        //do nothing
                    }
                }
                ItemStack deed = new WarpStone().asItem();
                deed.setAmount(amt);
                player.getInventory().addItem(deed);
            }else{
                Notifications.sendWarning(player, "You do not have permission for this command");
            }
        }

        return false;
    }

}

