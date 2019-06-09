package com.eclipsekingdom.warpmagic.global;

import com.eclipsekingdom.warpmagic.WarpMagic;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSpawn implements CommandExecutor {

    public CommandSpawn(WarpMagic plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {


        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location spawn = globalManager.getSpawn();
            if(spawn != null){
                plugin.getTeleportation().sendTo(player, spawn);
            }else{
                player.sendMessage(ChatColor.RED + "spawn point not set");
            }
        }

        return false;
    }

    private GlobalManager globalManager = GlobalManager.getInstance();

    private final WarpMagic plugin;

}
