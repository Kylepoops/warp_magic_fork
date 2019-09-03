package com.eclipsekingdom.warpmagic.global;

import com.eclipsekingdom.warpmagic.WarpMagic;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandNorth implements CommandExecutor {

    private static GlobalManager globalManager = GlobalManager.getInstance();
    private WarpMagic plugin;

    public CommandNorth(){
        this.plugin = WarpMagic.plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            Location north = globalManager.getNorth();
            if(north != null){
                plugin.getTeleportation().sendTo(player, north);
            }else{
                player.sendMessage(ChatColor.RED + "North point not set");
            }
        }

        return false;
    }

}
