package com.eclipsekingdom.warpmagic.loot;

import com.eclipsekingdom.warpmagic.sys.Permissions;
import com.eclipsekingdom.warpmagic.sys.lang.Message;
import com.eclipsekingdom.warpmagic.util.Amount;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandStone implements CommandExecutor {

    private ILoot loot;

    public CommandStone(ILoot loot) {
        this.loot = loot;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (Permissions.canSummonLoot(sender)) {
            if (args.length > 0) {
                String argOne = args[0];
                try {
                    int amount = Integer.parseInt(argOne);
                    if (sender instanceof Player) {
                        giveLoot((Player) sender, amount);
                    } else {
                        sender.sendMessage(ChatColor.RED + Message.MISC_FORMAT.fromFormat("/" + loot.getNamespace() + " [" + Message.ARG_PLAYER + "] [" + Message.ARG_AMOUNT + "]"));
                    }
                } catch (Exception e) {
                    Player player = Bukkit.getServer().getPlayer(argOne);
                    if (player != null) {
                        int amount = args.length > 1 ? Amount.parse(args[1]) : 1;
                        giveLoot(player, amount);
                        sender.sendMessage(ChatColor.GREEN + Message.SUCCESS_ITEMS_SENT.fromPlayer(player.getName()));
                    } else {
                        player.sendMessage(ChatColor.RED + Message.WARN_PLAYER_NOT_FOUND.fromPlayer(argOne));
                    }
                }
            } else {
                if (sender instanceof Player) {
                    giveLoot((Player) sender, 1);
                } else {
                    sender.sendMessage(ChatColor.RED + Message.MISC_FORMAT.fromFormat("/" + loot.getNamespace() + " [" + Message.ARG_PLAYER + "] [" + Message.ARG_AMOUNT + "]"));
                }
            }
        }

        return false;
    }

    private void giveLoot(Player player, int amount) {
        ItemStack item = loot.buildGeneric();
        item.setAmount(amount);
        player.getInventory().addItem(item);
    }

}
