package com.eclipsekingdom.warpmagic.util.commands;

import com.eclipsekingdom.warpmagic.Permissions;
import com.eclipsekingdom.warpmagic.util.communication.Notifications;
import com.eclipsekingdom.warpmagic.util.loot.Loot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class SummonLootCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player player = (Player) sender;
            if(Permissions.canSummonLoot(player)){
                int amt = 1;
                if(args.length > 0){
                    try{
                        amt = Integer.parseInt(args[0]);
                        if(amt < 0){
                            amt = 0;
                        }
                    }catch (Exception e){
                        //do nothing
                    }
                }
                ItemStack item = loot.asItem();
                item.setAmount(amt);
                player.getInventory().addItem(item);
            }else{
                Notifications.sendWarning(player, "You do not have permission for this command");
            }
        }

        return false;
    }

    protected abstract Loot initLoot();
    private final Loot loot = initLoot();

}
