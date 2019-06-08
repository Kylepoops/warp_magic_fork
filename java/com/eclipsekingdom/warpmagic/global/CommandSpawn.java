package com.eclipsekingdom.warpmagic.global;

import com.eclipsekingdom.warpmagic.Teleportation;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSpawn implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {


        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location spawn = globalManager.getSpawn();
            if(spawn != null){
                Teleportation.sendTo(player, spawn);
            }else{
                player.sendMessage(ChatColor.RED + "spawn point not set");
            }
        }

        return false;
    }

    private GlobalManager globalManager = GlobalManager.getInstance();


}
