package com.eclipsekingdom.warpmagic.global;

import com.eclipsekingdom.warpmagic.WarpMagic;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandWest implements CommandExecutor {

    private static GlobalManager globalManager = GlobalManager.getInstance();
    private WarpMagic plugin;

    public CommandWest(){
        this.plugin = WarpMagic.plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location west = globalManager.getWest();
            if(west != null){
                plugin.getTeleportation().sendTo(player, west);
            }else{
                player.sendMessage(ChatColor.RED + "West point not set");
            }
        }

        return false;
    }
}
