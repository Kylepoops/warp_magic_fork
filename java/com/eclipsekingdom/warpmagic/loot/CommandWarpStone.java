package com.eclipsekingdom.warpmagic.loot;

import com.eclipsekingdom.warpmagic.Permissions;
import com.eclipsekingdom.warpmagic.util.language.Message;
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
            if(Permissions.canSummonLoot(player)){
                ItemStack item = LootType.WARP_STONE.getLoot().buildGeneric();
                int amount = args.length > 0 ? Amount.parse(args[0]): 1;
                item.setAmount(amount);
                player.getInventory().addItem(item);
            }else{
                player.sendMessage(Message.ERROR_NOT_ALLOWED.get());
            }
        }

        return false;
    }

}
