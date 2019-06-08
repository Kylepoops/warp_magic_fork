package com.eclipsekingdom.warpmagic.global;

import com.eclipsekingdom.warpmagic.Teleportation;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHub implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {


        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location hub = globalManager.getHub();
            if(hub != null){
                Teleportation.sendTo(player, hub);
            }else{
                player.sendMessage(ChatColor.RED + "hub point not set");
            }
        }

        return false;
    }

    private GlobalManager globalManager = GlobalManager.getInstance();

}
